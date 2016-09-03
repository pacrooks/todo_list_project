require('minitest/autorun')
require('minitest/rg')
require('pry-byebug')
require_relative('../models/tasks')
require_relative('../models/categories')
require_relative('../models/users')
require_relative('../models/executives')
require_relative('../db/sql_runner')

# Tests for CRUD functionality in the tasks model

class TestCategories < Minitest::Test
  # def self.test_order
  #  :alpha
  # end

  def setup
    Task.destroy()  # delete everything before the test
    Category.destroy()
    User.destroy()
    Executive.destroy()

    @unassigned_user_id = User.get_unassigned_id()
    @unassigned_executive_id = Executive.get_unassigned_id()
    @unassigned_category_id = Category.get_unassigned_id()

    @category1 = Category.new( { 'name' => 'work', 'colour' => 'blue' } )
    @category1.save()
    @category2 = Category.new( { 'name' => 'films', 'colour' => 'yellow' } )
    @category2.save()

    @user1 = User.new( { 'name' => 'Matthew', 'loginid' => 'matt' } )
    @user1.save()
    @user2 = User.new( { 'name' => 'Mark', 'loginid' => 'mark' } )
    @user2.save()

    @executive1 = Executive.new( { 'name' => 'Group 1' } )
    @executive1.save()

    @task1 = Task.new ( {
      'headline' => 'Stop for tea',
      'description' => 'Remember to eat something today',
      'create_date' => '2016-08-25',
      'target_date' => nil,
      'priority' => nil,
      'status' => 1,
      'created_by_user_id' => @user1.id,
      'category_id' => @unassigned_category_id,
      'allocated_executive_id' => @unassigned_executive_id,
      'allocated_user_id' => @unassigned_user_id,
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
      'allocated_executive_id' => @executive1.id,
      'allocated_user_id' => @unassigned_user_id,
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
      'allocated_executive_id' => @executive1.id,
      'allocated_user_id' => @user1.id,
      'status' => 2,
      'is_deleted' => false
      } )
  end
  
  def test_01_initialize
    # Test an artist can been created and fields can be accessed
    assert_equal("Do someting sensible and ordinary", @task3.headline)
    assert_equal("Make a cup of tea", @task3.description)
    assert_equal("2016-01-01", @task3.create_date)
    assert_equal("2016-09-24", @task3.target_date)
    assert_equal(1, @task3.priority)
    assert_equal(2, @task3.status)
    assert_equal(@user2.id, @task3.created_by_user_id)
    assert_equal(@category2.id, @task3.category_id)
    assert_equal(@executive1.id, @task3.allocated_executive_id)
    assert_equal(@user1.id, @task3.allocated_user_id)
    assert_equal(false, @task3.is_deleted)
    assert_equal(0, @task3.id)
  end

  def test_02_save
    assert_equal(true, @task2.id > 0)
    @task3.save()
    assert_equal(@task2.id + 1, @task3.id)
  end

  def test_03_retrieve
    @task3.save()
    tasks = Task.all
    assert_equal(3, tasks.count)
    task = tasks.last
    assert_equal(@task3.headline, task.headline)
    assert_equal(@task3.description, task.description)
    assert_equal(@task3.create_date, task.create_date)
    assert_equal(@task3.target_date, task.target_date)
    assert_equal(@task3.priority, task.priority)
    assert_equal(@task3.status, task.status)
    assert_equal(@task3.created_by_user_id, task.created_by_user_id)
    assert_equal(@task3.category_id, task.category_id)
    assert_equal(@task3.allocated_executive_id, task.allocated_executive_id)
    assert_equal(@task3.allocated_user_id, task.allocated_user_id)
    assert_equal(@task3.is_deleted, task.is_deleted)
  end

  def test_04_update
    @task1.allocated_user_id = @user1.id
    @task1.allocated_executive_id = @executive1.id
    @task1.is_deleted = true
    @task1.update
    task = Task.by_id(@task1.id)
    assert_equal(@user1.id, task.allocated_user_id)
    assert_equal(@executive1.id, task.allocated_executive_id)
    assert_equal(true, task.is_deleted)
    task.is_deleted = false
    task.update
    task = Task.by_id(@task1.id)
    assert_equal(false, task.is_deleted)
  end

  def test_05_tasks_by_allocated_user_id()
    @task3.save()
    tasks = Task.by_allocated_user_id(@unassigned_user_id)
    assert_equal(1, tasks.count)
    tasks = Task.by_allocated_user_id(@unassigned_user_id, false)
    assert_equal(2, tasks.count)
  end

  def test_06_tasks_by_allocated_executive_id ()
    @task3.save()
    tasks = Task.by_allocated_executive_id(@executive1.id)
    assert_equal(1, tasks.count)
    tasks = Task.by_allocated_executive_id(@executive1.id, false)
    assert_equal(2, tasks.count)
  end

  def test_07_tasks_by_creator_id ()
    @task3.save()
    tasks = Task.by_creator_id(@user1.id)
    assert_equal(1, tasks.count)
    tasks = Task.by_creator_id(@user1.id, false)
    assert_equal(2, tasks.count)
  end

  def test_08_deleted_tasks()
    @task3.is_deleted = true
    @task3.save()
    tasks = Task.deleted_tasks()
    assert_equal(2, tasks.count)
  end

  def test_09_sort_by_priority
    Task.set_sort_key("priority")
    @task3.save
    tasks = Task.all
    assert_equal(3, tasks.count)
    tasks.sort!
    assert_equal("Stop for tea", tasks.first.headline)
    assert_equal("Do something exciting", tasks.last.headline)
  end

  def test_10_sort_by_create_date
    Task.set_sort_key("create_date")
    @task3.save
    tasks = Task.all
    assert_equal(3, tasks.count)
    tasks.sort!
    assert_equal("Do someting sensible and ordinary", tasks.first.headline)
    assert_equal("Do something exciting", tasks.last.headline)
  end

  def test_09_delete
    @task1.delete()
    @task2.delete()
    tasks = Task.all
    assert_equal(0, tasks.count)
  end
end