require_relative( '../db/db_interface' )
require_relative( 'categories')
require_relative( 'notes')
require ('pry-byebug')

class Task
  TABLE = "tasks"
  @@primary_sort_key = nil

  attr_reader :id
  attr_accessor :headline, :description, :create_date, :target_date, :priority, :is_deleted, :status, :created_by_user_id, :category_id, :allocated_executive_id, :allocated_user_id

  def save()
    @id = DbInterface.insert(TABLE, self)
    return @id
  end

  def update()
    return DbInterface.update(TABLE, self)
  end

  def delete()
    @id = Task.destroy(@id) if @id
    return @id
  end

  def to_hash ()
    hash = {}
    self.instance_variables.each {|var| hash[var.to_s.delete("@")] =
      self.instance_variable_get(var) }
    return hash
  end

  def get_sort_value()
    return instance_variable_get(("@" + Task.get_sort_key()).intern)
  end

  def <=>( neighbour )
    return get_sort_value() <=> neighbour.get_sort_value()
  end

  def created_by?( user_id )
    return (user_id == @created_by_user_id)
  end

  def allocated_to?( user_id )
    return (user_id == @allocated_user_id)
  end

  def initialize( options )
    @id = options['id'].to_i
    @headline = options ['headline']
    @description = options['description']
    @create_date = options['create_date'] == "null" ? nil : options['create_date']
    @target_date = options['target_date'] == "null" ? nil : options['target_date']
    @priority = options['priority'].to_i
    @status = options['status'].to_i
    @created_by_user_id = options['created_by_user_id'].to_i
    @category_id = options['category_id'].to_i == 0 ? Category.get_unassigned_id() : options['category_id'].to_i
    @allocated_executive_id = options['allocated_executive_id'].to_i
    @allocated_user_id = options['allocated_user_id'].to_i
    # @is_deleted will be boolean values when first created but "f" or "t" from the database
    @is_deleted = options['is_deleted'].to_s[0] == DbInterface::DB_TRUE
  end

  # Class methods start here

  def self.all( exclude_deleted = false )
    tasks = DbInterface.select( TABLE )
    tasks = tasks.select { |t| t['is_deleted'] == DbInterface::DB_FALSE } if exclude_deleted
    return tasks.map{ |t| Task.new( t ) }
  end

  def self.by_id( id )
    tasks = DbInterface.select( TABLE, id )
    return nil if tasks.count == 0
    return Task.new( tasks.first )
  end

  def self.by_allocated_user_id( user_id, exclude_deleted = true )
    tasks = DbInterface.select( TABLE, user_id, "allocated_user_id" )
    tasks = tasks.select { |t| t['is_deleted'] == DbInterface::DB_FALSE } if exclude_deleted
    return tasks.map{ |t| Task.new( t ) }
  end

  def self.by_allocated_executive_id ( executive_id, exclude_deleted = true )
    tasks = DbInterface.select( TABLE, executive_id, "allocated_executive_id" )
    tasks = tasks.select { |t| t['is_deleted'] == DbInterface::DB_FALSE } if exclude_deleted
    return tasks.map{ |t| Task.new( t ) }
  end

  def self.by_creator_id ( user_id, exclude_deleted = true )
    tasks = DbInterface.select( TABLE, user_id, "created_by_user_id" )
    tasks = tasks.select { |t| t['is_deleted'] == DbInterface::DB_FALSE } if exclude_deleted
    return tasks.map{ |t| Task.new( t ) }
  end

  def self.deleted_tasks()
    tasks = DbInterface.select( TABLE, DbInterface::DB_TRUE, "is_deleted" )
    return tasks.map{ |t| Task.new( t ) }
  end

  def self.destroy( id = nil )
    DbInterface.delete( TABLE, id )
    return nil
  end

  def self.set_sort_key( field_name )
    @@primary_sort_key = field_name
  end

  def self.get_sort_key()
    @@primary_sort_key ||= "priority"
    return @@primary_sort_key
  end
end