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

get '/' do
  redirect( to( '/sessions/login' ) )
end