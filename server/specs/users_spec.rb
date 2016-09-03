require('minitest/autorun')
require('minitest/rg')
require_relative('../models/users')
require_relative('../db/sql_runner')

# Tests for CRUD functionality in the users model

class Testusers < Minitest::Test
  # def self.test_order
  #  :alpha
  # end

  def setup
    User.destroy()  # delete everything before the test
    @user1 = User.new( { 'name' => 'Matthew', 'userid' => 'matt' } )
    @user2 = User.new( { 'name' => 'Mark', 'userid' => 'mark' } )
    @user1.save()
  end
  
  def test_01_initialize
    # Test an artist can been created and fields can be accessed
    assert_equal("Mark", @user2.name)
    assert_equal("mark", @user2.userid)
    assert_equal(0, @user2.id)
  end

  def test_02_save
    assert_equal(true, @user1.id > 0)
    @user2.save()
    assert_equal(@user1.id + 1, @user2.id)
  end

  def test_03_retrieve
    @user2.save()
    users = User.all
    assert_equal(2, users.count)
    user = users.last
    assert_equal(@user2.name, user.name)
    assert_equal(@user2.userid, user.userid)
  end

  def test_04_update
    @user1.name = "Luke"
    @user1.userid = "luke"
    @user1.update
    user = User.by_id(@user1.id)
    assert_equal("Luke", user.name)
    assert_equal("luke", user.userid)
  end

  def test_05_unassigned
    id = User.get_unassigned_id()
    assert_equal(true, id != nil)
    User.set_unassigned_id()
    assert_equal(true, id == User.get_unassigned_id())
  end

  def test_06_delete
    @user1.delete()
    users = User.all
    assert_equal(0, users.count)
  end

end