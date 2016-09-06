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

  @user1 = User.new( { 'name' => 'Matthew', 'userid' => 'matt', 'password' => 'matt' } )
  @user1.save()
  @user2 = User.new( { 'name' => 'Mark', 'userid' => 'mark', 'password' => '' } )
  @user2.save()

  @category1 = Category.new( { 'name' => 'work', 'colour' => 'blue', 'created_by_user_id' => @user1.id } )
  @category1.save()
  @category2 = Category.new( { 'name' => 'films', 'colour' => 'yellow', 'created_by_user_id' => @user1.id } )
  @category2.save()

  @task1 = Task.new ( {
    'headline' => 'Stop for tea',
    'description' => 'Remember to eat something today',
    'create_date' => '2016-08-25',
    'target_date' => nil,
    'priority' => nil,
    'status' => 1,
    'created_by_user_id' => @user1.id,
    'category_id' => @category1.id,
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
    'category_id' => @category1.id,
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
    'category_id' => @category2.id,
    'allocated_executive_id' => @user1.my_executive_id,
    'allocated_user_id' => @user2.id,
    'status' => 2,
    'is_deleted' => false
    } )
  @task3.save()

  @note1 = Note.new( { 'create_date' => '2016-02-02', 'text' => 'some extremely interesting text', 'task_id' => @task3.id } )
  @note1.save
  @note2 = Note.new( { 'create_date' => '2016-02-03', 'text' => 'the snow was all yellow',  'task_id' => @task3.id } )
  @note2.save
end

setup()