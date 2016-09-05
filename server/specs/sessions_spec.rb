require('minitest/autorun')
require('minitest/rg')
require_relative('../models/sessions')
require_relative('../models/users')
require_relative('../models/executives')
require_relative('../models/memberships')
require_relative('../models/tasks')
require_relative('../db/sql_runner')

# Tests for CRUD functionality in the sessions model

class Testsessions < Minitest::Test
  # def self.test_order
  #  :alpha
  # end

  def setup
    Task.destroy()
    Membership.destroy()
    Session.destroy()  # delete everything before the test
    User.destroy()
    Executive.destroy()

    @time1 = Time.now
    @user1 = User.new( { 'name' => 'Matthew', 'userid' => 'matt' } )
    @user1.save()
    @session1 = Session.new( { 'last_used' => @time, 'user_id' => User.get_unassigned_id() } )
    @sessionid = Session.make_sessionid();
    @session2 = Session.new( { 'last_used' => @time, 'sessionid' => @sessionid, 'user_id' => @user1.id } )
    @session1.save()
  end
  
  def test_01_initialize
    # Test an artist can been created and fields can be accessed
    assert_equal(@user1.id, @session2.user_id)
    assert_equal(@sessionid, @session2.sessionid)
    assert_equal(@time, @session2.last_used)
    assert_equal(0, @session2.id)
  end

  def test_02_save
    assert_equal(true, @session1.id > 0)
    @session2.save()
    assert_equal(@session1.id + 1, @session2.id)
  end

  def test_03_retrieve
    @session2.save()
    sessions = Session.all
    assert_equal(2, sessions.count)
    session = sessions.last
    assert_equal(@session2.sessionid, session.sessionid)
    assert_equal(@session2.user_id, session.user_id)
    assert_equal(@session2.last_used, session.last_used)
  end

  def test_04_update
    session_id = Session.make_sessionid()
    assert_equal(false , session_id == "")
    time = Time.now
    @session1.sessionid = session_id
    @session1.last_used = time
    @session1.update
    session = Session.by_id(@session1.id)
    assert_equal(session_id, session.sessionid)
    # assert_equal(time, session.last_used)
  end

  def test_05_delete
    @session1.delete()
    sessions = Session.all
    assert_equal(0, sessions.count)
  end

end