require_relative( '../db/db_interface' )

class Category
  TABLE = "categories"
  UNASSIGNED = "unassigned"
  @@unassigned_id = nil

  attr_reader :id
  attr_accessor :name, :colour

  def save()
    @id = DbInterface.insert(TABLE, self)
    return @id
  end

  def update()
    return DbInterface.update(TABLE, self)
  end

  def delete()
    @id = Category.destroy(@id) if @id
    return @id
  end

  def to_hash()
    options = Hash.new
    options['id'] = @id
    options['name'] = @name
    options['colour'] = @colour
    return options
  end

  def initialize( options )
    @id = options['id'].to_i
    @name = options['name']
    @colour = options['colour']
  end

  # Class methods start here

  def self.all()
    categories = DbInterface.select( TABLE )
    return categories.map{ |c| Category.new( c ) }
  end

  def self.by_id( id )
    categories = DbInterface.select( TABLE, id )
    return nil if categories.count == 0
    return Category.new( categories.first )
  end

  def self.by_name( name )
    categories = DbInterface.select( TABLE, name, "name" )
    return nil if categories.count == 0
    return Category.new( categories.first )
  end

  def self.destroy( id = nil )
    return @@unassigned_id if (id && id == @@unassigned_id)
    @@unassigned_id = nil if !id
    DbInterface.delete( TABLE, id )
    return nil
  end

  def self.set_unassigned_id()
    if !@@unassigned_id
      category = Category.by_name( UNASSIGNED )
      category = Category.new( { 'name' => UNASSIGNED } ) if !category
      category.save
      @@unassigned_id = category.id
    end
  end

  def self.get_unassigned_id()
    @@unassigned_id = Category.set_unassigned_id() if !@@unassigned_id
    return @@unassigned_id
  end
end