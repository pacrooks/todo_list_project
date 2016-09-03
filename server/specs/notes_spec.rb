require('minitest/autorun')
require('minitest/rg')
require_relative('../models/notes')
require_relative('../db/sql_runner')

# Tests for CRUD functionality in the notes model


class TestCategories < Minitest::Test
  # def self.test_order
  #  :alpha
  # end

  def setup
    Note.destroy()  # delete everything before the test
    # need to creat a task
    @task = Task.new()
    @note1 = Note.new( { 'create_date' => '??????', 'text' => 'some extremely interesting text', 'task_id' => @task.id } )
    @note2 = Note.new( { 'create_date' => '??????', 'text' => 'the snow was all yellow',  'task_id' => @task.id } )
    @note1.save()
  end
  
  def test_01_initialize
    # Test an artist can been created and fields can be accessed
    assert_equal("films", @note2.create_date)
    assert_equal("yellow", @note2.text)
    assert_equal(@task.id, @note2.task_id)
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
    assert_equal("films", @note2.create_date)
    assert_equal("yellow", @note2.text)
    assert_equal(@task.id, @note2.task_id)
  end

  def test_04_update
    @note1.name = "fishing"
    @note1.colour = "grey"
    @note1.update
    note = Note.by_id(@note1.id)
    assert_equal("fishing", note.create_date)
    assert_equal("grey", note.text)
  end

  def test_05_delete
    @note1.delete()
    notes = Note.all
    assert_equal(0, notes.count)
  end
end