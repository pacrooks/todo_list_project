require('minitest/autorun')
require('minitest/rg')
require_relative('../models/tasks')
require_relative('../models/categories')
require_relative('../models/users')
require_relative('../models/executives')
require_relative('../models/notes')
require_relative('../models/sessions')

# Tests for CRUD functionality in the memberships model
# The artist is the simplets model with no dependency on any other table
# This test should be run first as other test will depend on it.

class Testusers < Minitest::Test
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

    @user1 = User.new( { 'name' => 'Matthew', 'userid' => 'matt' } )
    @user1.save()
    @user2 = User.new( { 'name' => 'Mark', 'userid' => 'mark' } )
    @user2.save()
    @executive1 = Executive.new( { 'name' => 'Matthew' } )
    @executive1.save()
    @executive2 = Executive.new( { 'name' => 'Mark' } )
    @executive2.save()
    @executive3 = Executive.new( { 'name' => 'Group1' } )
    @executive3.save()
    @membership1 = Membership.new( { 'user_id' => @user1.id, 'executive_id' => @executive1.id } )
    @membership1.save()
    @membership2 = Membership.new( { 'user_id' => @user2.id, 'executive_id' => @executive2.id } )
    @membership2.save()
    @membership3 = Membership.new( { 'user_id' => @user1.id, 'executive_id' => @executive3.id } )
    @membership3.save()
    @membership4 = Membership.new( { 'user_id' => @user2.id, 'executive_id' => @executive3.id } )
  end
  
  def test_01_initialize
    # Test an artist can been created and fields can be accessed
    assert_equal(@user2.id, @membership4.user_id)
    assert_equal(@executive3.id, @membership4.executive_id)
    assert_equal(0, @membership4.id)
  end

  def test_02_save
    assert_equal(true, @membership3.id > 0)
    @membership4.save()
    assert_equal(@membership3.id + 1, @membership4.id)
  end

  def test_03_retrieve
    memberships = Membership.all
    # Two implict and three explicit memberships
    assert_equal(5, memberships.count)
    membership = memberships.last
    assert_equal(@membership3.user_id, membership.user_id)
    assert_equal(@membership3.executive_id, membership.executive_id)
  end

  def test_04_update
    @membership1.user_id = @user2.id
    @membership1.executive_id = @executive2.id
    @membership1.update
    membership = Membership.by_id(@membership1.id)
    assert_equal(@user2.id, membership.user_id)
    assert_equal(@executive2.id, membership.executive_id)
  end

  def test_05_delete
    @membership1.delete()
    @membership2.delete()
    @membership3.delete()
    memberships = Membership.all
    # Two memberships are implicitly created for users
    assert_equal(2, memberships.count)
  end

  def test_06_users_by_executive
    @membership4.save()
    users = Membership.users_by_executive_id(@executive3.id)
    assert_equal(2, users.count)
    users = Membership.users_by_executive_id(@executive1.id)
    assert_equal(1, users.count)
    assert_equal(@user1.name, users.first.name)
  end

  def test_07_executives_by_user
    @membership4.save
    executives = Membership.executives_by_user_id(@user1.id)
    # Two explicit memberships and one implicit membership created for user1
    assert_equal(3, executives.count)
  end

end