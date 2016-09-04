require_relative( '../db/db_interface' )
require( 'pry-byebug' )

class Membership
  TABLE = "memberships"

  attr_reader :id
  attr_accessor :user_id, :executive_id

  def save()
    @id = DbInterface.insert(TABLE, self)
    return @id
  end

  def update()
    return DbInterface.update(TABLE, self)
  end

  def delete()
    @id = Membership.destroy(@id) if @id
    return @id
  end

  def initialize( options )
    @id = options['id'].to_i
    @user_id = options['user_id'].to_i
    @executive_id = options['executive_id'].to_i
  end

  # Class methods start here

  def self.all()
    memberships = DbInterface.select( TABLE )
    return memberships.map{ |m| Membership.new( m ) }
  end

  def self.by_id( id )
    memberships = DbInterface.select( TABLE, id )
    return nil if memberships.count == 0
    return Membership.new( memberships.first )
  end

  def self.executives_by_user_id ( user_id )
    table = "memberships INNER JOIN executives ON memberships.executive_id = executives.id"
    sql = "SELECT executives.* FROM #{table} WHERE users.id = #{user_id}"
    executives = SqlRunner.run( sql )
    return executives.map{ |u| Executive.new( u ) }
  end

  def self.users_by_executive_id ( executive_id )
    table = "memberships INNER JOIN users ON memberships.user_id = users.id"
    sql = "SELECT users.* FROM #{table} WHERE executives.id = #{executive_id}"
    users = SqlRunner.run( sql )
    return users.map{ |u| User.new( u ) }
  end

  def self.is_member?( user_id, executive_id )
    members = Membership.users_by_executive_id(executive_id)
    return (members.select{ |m| m.user_id == user_id }.count > 0)
  end

  def self.destroy( id = nil )
    DbInterface.delete( TABLE, id )
    return nil
  end
end