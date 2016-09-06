require('minitest/autorun')
require('minitest/rg')
require_relative('../models/tasks')
require_relative('../models/categories')
require_relative('../models/users')
require_relative('../models/executives')
require_relative('../models/notes')
require_relative('../models/sessions')

# Tests for CRUD functionality in the categories model

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
    @category2 = Category.new( { 'name' => 'films', 'colour' => 'yellow', 'created_by_user_id' => @user1.id } )
  end
  
  def test_01_initialize
    # Test an artist can been created and fields can be accessed
    assert_equal("films", @category2.name)
    assert_equal("yellow", @category2.colour)
    assert_equal(0, @category2.id)
  end

  def test_02_save
    assert_equal(true, @category1.id > 0)
    @category2.save()
    assert_equal(@category1.id + 1, @category2.id)
  end

  def test_03_retrieve
    @category2.save()
    categories = Category.all
    assert_equal(2, categories.count)
    category = categories.last
    assert_equal(@category2.name, category.name)
    assert_equal(@category2.colour, category.colour)
  end

  def test_04_update
    @category1.name = "fishing"
    @category1.colour = "grey"
    @category1.update
    category = Category.by_id(@category1.id)
    assert_equal("fishing", category.name)
    assert_equal("grey", category.colour)
  end

  def test_05_delete
    @category1.delete()
    categories = Category.all
    assert_equal(0, categories.count)
  end
end