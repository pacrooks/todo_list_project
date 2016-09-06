require('sinatra')
require('sinatra/contrib/all')
require ('json')
require_relative('models/sessions')
require_relative('models/tasks')
require_relative('models/users')
require_relative('controllers/sessions')
require_relative('controllers/tasks')
require_relative('controllers/groups')
require ('open-uri')
require('pry-byebug')

def get_user( session_id )
  session = Session.by_sessionid(session_id)
  return nil if !session
  session.last_used = Time.now
  return session.get_user()
  # return User.all.first
end

get '/' do
  redirect( to( '/sessions/login' ) )
end