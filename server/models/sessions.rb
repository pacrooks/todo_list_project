
require('securerandom')
require_relative( '../db/db_interface' )


class Session
  TABLE = "sessions"

  attr_reader :id
  attr_accessor :sessionid, :user_id

  def last_used=( time )
    @last_used = time.to_s
  end

  def last_used()
    return @last_used
  end

  def save()
    @id = DbInterface.insert(TABLE, self)
    return @id
  end

  def update()
    return DbInterface.update(TABLE, self)
  end

  def delete()
    @id = Session.destroy(@id) if @id
    return @id
  end

  def get_user()
    return User.by_id(@user_id)
  end
  
  def initialize( options )
    @id = options['id'].to_i
    @sessionid = options['sessionid']
    @last_used = options['last_used'].to_s
    @user_id = options['user_id'].to_i
  end

  # Class methods start here

  def self.make_sessionid()
    session_id = nil
    loop do
      session_id = SecureRandom.uuid()
      session = Session.by_sessionid(session_id)
      break if !session
    end
    return session_id
  end

  def self.all()
    sessions = DbInterface.select( TABLE )
    return sessions.map{ |s| Session.new( s ) }
  end

  def self.by_id( id )
    sessions = DbInterface.select( TABLE, id )
    return nil if sessions.count == 0
    return Session.new( sessions.first )
  end

  def self.by_sessionid( session_id )
    sessions = DbInterface.select( TABLE, session_id, "sessionid" )
    return nil if sessions.count == 0
    return Session.new( sessions.first )
  end

  def self.destroy( id = nil )
    DbInterface.delete( TABLE, id )
    return nil
  end
end