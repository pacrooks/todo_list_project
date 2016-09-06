# RESTful code for the artists pages

# CREATE
post '/categories' do
  # The user has POSTed to create a new category
  user = get_user(params['sessionid'])
  if user
    status 404
  else
    status 401
  end
end

# INDEX
get '/categories' do
  # The user wants to see all the categories
  user = get_user(params['sessionid'])
  if user
    categories = Category.all().sort
    categories.map!{ | c |  c.id }
    content_type :json
    categories.to_json
  else
    status 401
  end

end

# SHOW
get '/categories/:id' do
#   # The user wants to see the details for one category
  user = get_user(params['sessionid'])
  if user
    category = Category.by_id(params['id'].to_i)
    if category 
      content_type :json
      category.to_hash().to_json()
    else
        # Can't find the resource
      status 404
    end
  else
    # No session information for the user
    status 401
  end
end

# UPDATE
post '/categories/:id' do
  user = get_user(params['sessionid'])
  if user
    status 404
  else
    status 401
  end
end

# DELETE
post '/categories/:id/delete' do
  user = get_user(params['sessionid'])
  if user
    status 404
  else
    status 401
  end
end