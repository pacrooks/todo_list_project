require('minitest/autorun')
require('minitest/rg')
require_relative('../models/tasks')
require_relative('../models/categories')
require_relative('../models/users')
require_relative('../models/executives')
require_relative('../models/notes')
require_relative('../models/sessions')

# Tests for CRUD functionality in the notes model


class TestCategories < Minitest::Test
  # def self.test_order
  #  :alpha
  # end

  def setup
    Note.destroy()
    Task.destroy()
    Category.destroy()
    Membership.destroy()
    User.destroy()
    Executive.destroy()
    Session.destroy()

    @user1 = User.new( { 'name' => 'Matthew', 'userid' => 'matt', 'password' => 'matt' } )
    @user1.save()
    @category1 = Category.new( { 'name' => 'work', 'colour' => 'blue', 'created_by_user_id' => @user1.id } )
    @category1.save()
    # need to creat a task
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
    @task1.save();
    @note1 = Note.new( { 'create_date' => '1918-11-11', 'text' => 'some extremely interesting text', 'task_id' => @task1.id } )
    @note2 = Note.new( { 'create_date' => '2000-01-01', 'text' => 'the snow was all yellow',  'task_id' => @task1.id } )
    @note1.save()
  end
  
  def test_01_initialize
    # Test an artist can been created and fields can be accessed
    assert_equal("2000-01-01", @note2.create_date)
    assert_equal("the snow was all yellow", @note2.text)
    assert_equal(@task1.id, @note2.task_id)
    assert_equal(0, @note2.id)
  end

  def test_02_save
    assert_equal(true, @note1.id > 0)
    @note2.save()
    assert_equal(@note1.id + 1, @note2.id)
  end

  def test_03_retrieve
    @note2.save()
    notes = Note.all
    assert_equal(2, notes.count)
    note = notes.last
    assert_equal("2000-01-01", note.create_date)
    assert_equal("the snow was all yellow", note.text)
    assert_equal(@task1.id, @note.task_id)
  end

  def test_04_update
    @note1.create_date = "1963-09-24"
    @note1.text = "A great day."
    @note1.update
    note = Note.by_id(@note1.id)
    assert_equal("1963-09-24", note.create_date)
    assert_equal("A great day.", note.text)
  end

  def test_05_delete
    @note1.delete()
    notes = Note.all
    assert_equal(0, notes.count)
  end
end