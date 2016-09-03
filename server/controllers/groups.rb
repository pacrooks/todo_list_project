# RESTful code for the artists pages

# NEW
get '/groups/new' do
  # Show the user a form to enter artist details

end

# CREATE
post '/groups' do
  # The user has POSTed the stock NEW form

end

# INDEX
get '/groups' do
  # The user wants to see all the stock

end

# SHOW
get '/groups/:id' do
#   # The user wants to see the details for one artist

end

# EDIT
get '/groups/:id/edit' do
  # Show the user a form to edit an artist
  # Use the same form as the NEW page

end

# UPDATE
post '/groups/:id' do

end

# DELETE
post '/groups/:id/delete' do

end