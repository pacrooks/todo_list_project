# RESTful code for the artists pages

def access_allowed( user, task )
  # result = true
  result = (task.created_by?( user.id ) || task.allocated_to?( user.id ))
  if !result && task.allocated_executive_id
    result ||= Membership.is_member?( user.id, task.allocated_executive_id )
  end
  return result
end

# NEW
# Don't need to create any resources to allow the user to create a new task

# CREATE
post '/tasks' do
  user = get_user(params['sessionid'])
  if user
    # All newly created tasks are allocated to the creator by default
    # This can be edited later
    new_task = Task.new(params)
    new_task.created_by_user_id = user.id
    new_task.allocated_user_id = user.id
    new_task.allocated_executive_id = user.my_executive_id
    new_task.save()
    { :id => new_task.id }.to_json
  else
    # user does not exist
    status 401
  end
end

# INDEX
get '/tasks' do
  # The user wants to see all his tasks
  # Find the user making the request
  user = get_user(params['sessionid'])
  # Chack that the user exists
  if user
    # Read the params hash
    reverse_search = (params['reverse'].to_i == 1)
    category_filter = params['category'].to_i
    order_by = params['order']

    # Set the default sort key
    Task.set_sort_key("priority")
    # Update the search key with the user's selection (they may not have made one)
    Task.set_sort_key(order_by) if (order_by && order_by != "")
    # Retreieve a list of tasks
    tasks = Task.by_allocated_user_id(user.id)
    # Sort the list
    tasks.sort!
    # Check if the user wants to reverse the order
    tasks.reverse! if reverse_search
    # Search the sorted list for tasks of a specific category if the user has requested a filter
    tasks.select!{ | t | t.category_id == category_filter } if (category_filter && (category_filter > 0))
    # Create the JSON to send back to the user
    tasks.map!{ | t |  t.id }
    content_type :json
    tasks.to_json
  else
    # User doe not exist
    status 401
  end
end

# SHOW
get '/tasks/:id' do
  # The user wants to see the details for one artist
  user = get_user(params['sessionid'])
  if user
    task = Task.by_id(params['id'].to_i)
    if task && access_allowed(user, task)
      content_type :json
      task.to_hash().to_json()
    else
      # Can't find the resource or not allowed to see it
      status 404
    end
  else
    # No session information for the user
    status 401
  end

end

# EDIT
# Don't need to create any resources to allow the user to edit a task

# UPDATE
post '/tasks/:id' do
  user = get_user(params['sessionid'])
  if user
    task = Task.by_id(params['id'].to_i)
    if task && access_allowed(user, task)
      new_task = Task.new(params)
      # The next three fields are ignored by the client at the moment
      # They have to be set to keep the database happy
      new_task.created_by_user_id = user.id
      new_task.allocated_user_id = user.id
      new_task.allocated_executive_id = user.my_executive_id
      new_task.update()
      status 200
    else
      status 404
    end
  else
    # user does not exist
    status 401
  end

end

# DELETE
post '/tasks/:id/delete' do
  user = get_user(params['sessionid'])
  if user
    task = Task.by_id(params['id'].to_i)
    if task && access_allowed(user, task)
      if task.is_deleted
        # Is already marked as deleted
        # Now remove completely
        task.delete()
      else
        task.is_deleted = true
      end
      status 200
    else
      status 404
    end
  else
    # user does not exist
    status 401
  end
end