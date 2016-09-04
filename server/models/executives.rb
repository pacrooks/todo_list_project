require_relative( '../db/db_interface' )
require( 'pry-byebug' )

class Executive
  TABLE = "executives"
  UNASSIGNED = "unassigned"
  @@unassigned_id = nil

  attr_reader :id
  attr_accessor :name

  def save()
    @id = DbInterface.insert(TABLE, self)
    return @id
  end

  def update()
    return DbInterface.update(TABLE, self)
  end

  def delete()
    @id = Executive.destroy(@id) if @id
    return @id
  end

  def initialize( options )
    @id = options['id'].to_i
    @name = options['name']
  end

  # Class methods start here

  def self.all()
    executives = DbInterface.select( TABLE )
    return executives.map{ |ex| Executive.new( ex ) }
  end

  def self.by_id( id )
    executives = DbInterface.select( TABLE, id )
    return nil if executives.count == 0
    return Executive.new( executives.first )
  end

  def self.by_name( name )
    executives = DbInterface.select( TABLE, name, "name" )
    return nil if executives.count == 0
    return Executive.new( executives.first )
  end

  def self.destroy( id = nil )
    return @@unassigned_id if (id && id == @@unassigned_id)
    @@unassigned_id = nil if !id
    DbInterface.delete( TABLE, id )
    return nil
  end

  def self.set_unassigned_id()
    if !@@unassigned_id
      executive = Executive.by_name( UNASSIGNED )
      if !executive
        executive = Executive.new( { 'name' => UNASSIGNED } ).save
        executive.save
      end
      @@unassigned_id = executive.id
    end
  end

  def self.get_unassigned_id()
    @@unassigned_id = Executive.set_unassigned_id() if !@@unassigned_id
    return @@unassigned_id
  end
end