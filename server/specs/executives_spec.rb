require('minitest/autorun')
require('minitest/rg')
require_relative('../models/executives')
require_relative('../db/sql_runner')

# Tests for CRUD functionality in the executives model

class Testexecutives < Minitest::Test
  # def self.test_order
  #  :alpha
  # end

  def setup
    Executive.destroy()  # delete everything before the test
    @executive1 = Executive.new( { 'name' => 'Group 1' } )
    @executive2 = Executive.new( { 'name' => 'Group 2' } )
    @executive1.save()
  end
  
  def test_01_initialize
    # Test an artist can been created and fields can be accessed
    assert_equal("Group 2", @executive2.name)
    assert_equal(0, @executive2.id)
  end

  def test_02_save
    assert_equal(true, @executive1.id > 0)
    @executive2.save()
    assert_equal(@executive1.id + 1, @executive2.id)
  end

  def test_03_retrieve
    @executive2.save()
    executives = Executive.all
    assert_equal(2, executives.count)
    executive = executives.last
    assert_equal(@executive2.name, executive.name)
  end

  def test_04_update
    @executive1.name = "Group 3"
    @executive1.update
    executive = Executive.by_id(@executive1.id)
    assert_equal("Group 3", executive.name)
  end

  def test_05_unassigned
    id = Executive.get_unassigned_id()
    assert_equal(true, id != nil)
    Executive.set_unassigned_id()
    assert_equal(true, id == Executive.get_unassigned_id())
  end

  def test_06_delete
    @executive1.delete()
    executives = Executive.all
    assert_equal(0, executives.count)
  end
end