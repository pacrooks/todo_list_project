require_relative( '../db/db_interface' )

class Note
  TABLE = "notes"

  attr_reader :id
  attr_accessor :name, :loginid

  def save()
    @id = DbInterface.insert(TABLE, self)
    return @id
  end

  def update()
    return DbInterface.update(TABLE, self)
  end

  def delete()
    @id = Note.destroy(@id) if @id
    return @id
  end

  def to_hash ()
    hash = {}
    self.instance_variables.each {|var| hash[var.to_s.delete("@")] =
      self.instance_variable_get(var) }
    return hash
  end

  def initialize( options )
    @id = options['id'].to_i
    @create_date = options['create_date']
    @text = options['text']
    @task_id = options['task_id'].to_i
  end

  # Class methods start here

  def self.all()
    notes = DbInterface.select( TABLE )
    return notes.map{ |n| Note.new( n ) }
  end

  def self.by_id( id )
    notes = DbInterface.select( TABLE, id )
    return nil if notes.count == 0
    return Note.new( notes.first )
  end

  def self.by_task_id( task_id )
    notes = DbInterface.select( TABLE, task_id, "task_id" )
    return notes.map{ |n| Note.new( n ) }
  end

  def self.destroy( id = nil )
    DbInterface.delete( TABLE, id )
    return nil
  end
end