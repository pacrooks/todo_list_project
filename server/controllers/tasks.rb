# RESTful code for the artists pages

def get_user( session_id )
  session = Session.by_sessionid(session_id)
  return nil if !session
  session.last_used = Time.now
  return session.get_user()
  # return User.all.first
end

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
  user = get_user(params['sessionid'])
  if user
    # tasks = Task.by_allocated_user_id(user.id)
    tasks = Task.by_allocated_user_id(user.id)  # exclude deleted 
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