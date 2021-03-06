require_relative( 'sql_runner' )
require('pry-byebug')

class DbInterface
  DB_TRUE = "t"
  DB_FALSE = "f"

  def self.select( table, id = nil, field = "id" )
    sql = "SELECT * FROM #{table}"
    if id
      sql += " WHERE #{ field } = "
      sql += id.is_a?( Numeric ) ? "#{ id }" : "'#{id.gsub("'", "''")}'"
    end
    return SqlRunner.run( sql )
  end

  def self.delete ( table, id = nil )
    sql = "DELETE FROM #{table}"
    sql += " WHERE id = #{ id }" if id
    return SqlRunner.run( sql )
  end

  def self.insert ( table, object )
    strings = to_strings( object )
    sql = "INSERT INTO #{table} (" + strings[ :fields ] +
      ") VALUES (" + strings[ :values ] + ") RETURNING *"
    result = SqlRunner.run( sql ).first
    return result["id"].to_i
  end

  def self.update ( table, object )
    if object.id && object.id > 0
	    strings = to_strings( object )
      sql = "UPDATE #{table} SET (" + strings[ :fields ] + ") = (" +
        strings[ :values ] + ") WHERE id = #{object.id} RETURNING *"
      result = SqlRunner.run( sql ).first
      return result["id"].to_i
    else
      return nil
    end
  end

  private

  def self.to_hash ( object )
    hash = {}
    object.instance_variables.each {|var| hash[var.to_s.delete("@")] =
      object.instance_variable_get(var) }
    return hash
  end
  
  def self.to_strings ( object )
  	fields = values = ""
    hash = to_hash( object )
    for x, y in hash
      if y != nil && x != "id"
        fields += "#{x}, "
        values += y.is_a?( String ) ? "'%s', " % [y.gsub("'", "''")] : "#{y}, "
      end 
    end
    return { :fields => fields[0..-3], :values => values[0..-3] }
  end
end