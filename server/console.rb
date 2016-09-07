require_relative('models/tasks')
require_relative('models/categories')
require_relative('models/users')
require_relative('models/executives')
require_relative('models/notes')
require_relative('models/sessions')

def setup
  # delete everything before the test
  Note.destroy()
  Task.destroy()
  Category.destroy()
  Membership.destroy()
  User.destroy()
  Executive.destroy()
  Session.destroy()

  @user1 = User.new( { 'name' => 'Phil Crooks', 'userid' => 'phil', 'password' => 'phil' } )
  @user1.save()
  @user2 = User.new( { 'name' => 'Mark', 'userid' => 'mark', 'password' => '' } )
  @user2.save()

  @cat_work = Category.new( { 'name' => 'work', 'colour' => 'blue', 'created_by_user_id' => @user1.id } )
  @cat_work.save()
  @cat_films = Category.new( { 'name' => 'films', 'colour' => 'yellow', 'created_by_user_id' => @user1.id } )
  @cat_films.save()
  @cat_music = Category.new( { 'name' => 'music', 'colour' => 'green', 'created_by_user_id' => @user1.id } )
  @cat_music.save()
  @cat_family = Category.new( { 'name' => 'family', 'colour' => 'red', 'created_by_user_id' => @user1.id } )
  @cat_family.save()
  @cat_friends = Category.new( { 'name' => 'friends', 'colour' => 'magenta', 'created_by_user_id' => @user1.id } )
  @cat_friends.save()

  @task1 = Task.new ( {
    'headline' => 'Stop for tea',
    'description' => 'Remember to eat something today',
    'create_date' => '2016-08-25',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_work.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  @task1.save()
  @task2 = Task.new ( {
    'headline' => 'Do something exciting',
    'description' => nil,
    'create_date' => '2016-08-26',
    'target_date' => '2016-09-24',
    'priority' => 2,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_family.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => true
    } )
  @task2.save()
  @task3 = Task.new ( {
    'headline' => 'Do someting sensible and ordinary',
    'description' => 'Make a cup of tea',
    'create_date' => '2016-01-01',
    'target_date' => '2016-09-24',
    'priority' => 1,
    'created_by_user_id' => @user2.id,
    'category_id' => @cat_work.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user2.id,
    'status' => 2,
    'is_deleted' => false
    } )
  @task3.save()

  task = Task.new ( {
    'headline' => 'Buy more Marcus Miller',
    'description' => '',
    'create_date' => '2016-06-06',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_music.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Buy guitar strings',
    'description' => 'Buy Elixir acoustic 11s and 12s',
    'create_date' => '2016-06-06',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_music.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Get Dusk \'Til Dawn',
    'description' => 'Evie said she hadn\'t seen it',
    'create_date' => '2016-06-06',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_films.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Add more tasks to the server',
    'description' => 'Need at least 10 tasks to fill up the screen.',
    'create_date' => '2016-06-06',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_work.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Get details of Lumix TZ60',
    'description' => 'Dad is having trouble seeing his camera screen.',
    'create_date' => '2016-06-06',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_family.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Phone Doug',
    'description' => 'See how he\'s doing',
    'create_date' => '2016-06-06',
    'target_date' => '2016-06-10',
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_friends.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Meet Catriona in the Bow Bar',
    'description' => '',
    'create_date' => '2016-06-06',
    'target_date' => '2016-06-06',
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_friends.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Get \'The Third Man\' on Blu Ray',
    'description' => nil,
    'create_date' => '2016-06-06',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_films.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Get 4K Blu Ray player',
    'description' => '',
    'create_date' => '2016-06-06',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_films.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  task = Task.new ( {
    'headline' => 'Listen to the Thelonius Monk boxed set',
    'description' => nil,
    'create_date' => '2016-06-06',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @cat_music.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user1.id,
    'is_deleted' => false
    } )
  task.save()

  @note1 = Note.new( { 'create_date' => '2016-02-02', 'text' => 'some extremely interesting text', 'task_id' => @task3.id } )
  @note1.save
  @note2 = Note.new( { 'create_date' => '2016-02-03', 'text' => 'the snow was all yellow',  'task_id' => @task3.id } )
  @note2.save
end

setup()