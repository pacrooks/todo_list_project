
# RESTful code for sessions

# Ask for LOGIN
get '/sessions/login' do
  # A user wants to login.
  status 200
  # Supply a session id to start the session off
  # generate a response
  content_type :json
  { :sessionid => Session.make_sessionid() }.to_json
end

# LOGIN
post '/sessions' do
  # A login request has been received.
  # Check the username, password and session id
  # Generate an error if the data doesn't check out
  session_id = params['sessionid']
  user_id = params['userid']
  user_password = params['password']
  user = User.by_userid(user_id)
  if (user && user.check_password(user_password))
    session = Session.by_sessionid(session_id)
    if !session
      session = Session.new({
        'sessionid' => session_id,
        'user_id' => user.id,
        'last_used' => Time.now
        })
      session.save
      status 200
    else
      # Internal processing error. The session already exists
      status 500
    end
  else
    # The user id and password don't check out. Authentication error.
    status 401
  end
end

# LOGOUT
post '/sessions/logout' do
  # A user wants to end their session
  session_id = params['sessionid']
  session = Session.by_sessionid(session_id)
  if session
    session.delete
    status 200
  else
    status 404
  end
end