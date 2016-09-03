# RESTful code for the artists pages

def get_user( session_id )
  # session = Session.by_sessionid(session_id)
  # return nil if !session
  # return session.get_user()
  return User.all.first
end

# NEW
# Don't need to create any resources to allow the user to create a new task

# CREATE
post '/tasks' do
  # The user has POSTed the stock NEW form
  if user
    new_task = Task.new(params)
    new_task.save()
  else
    # user does not exist
  end
end

# INDEX
get '/tasks' do
  # The user wants to see all his tasks
  user = get_user(params['sessionid'])
  if user
    # tasks = Task.by_allocated_user_id(user.id)
    tasks = Task.all(true)  # exclude deleted 
    tasks.map{ | t |  t.to_hash() }.to_json
  else
    # User doe not exist
  end

end

# SHOW
get '/tasks/:id' do
  # The user wants to see the details for one artist
  user = get_user(params['sessionid'])
  if user
    task = Task.by_id(params['id'].to_i)
    if task
      task.to_hash().to_json()
    else
      # Can't find the resource
    end
  else
    # No session information for the user
  end

end

# EDIT
# Don't need to create any resources to allow the user to edit a task

# UPDATE
post '/tasks/:id' do
  user = get_user(params['sessionid'])
  if user
    task = Task.by_id(params['id'].to_i)
    if task
      new_task = Task.new(params)
      new_task.update()
    end
  else
    # user does not exist
  end

end

# DELETE
post '/tasks/:id/delete' do
  user = get_user(params['sessionid'])
  if user
    task = Task.by_id(params['id'].to_i)
    task.delete if task
  else
    # user does not exist
  end
end