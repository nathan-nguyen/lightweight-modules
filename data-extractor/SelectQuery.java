import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class SelectQuery {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<String> queryData(String query) {
		return (List<String>) (jdbcTemplate.query(query, new ResultSetExtractor() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> cList = new ArrayList<String>();
				while (rs.next()) {
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
						sb.append(rs.getObject(i));
						sb.append(";");
					}
					cList.add(sb.toString());
				}
				return cList;
			}
		}));
	}

	public void getData(String query) {
		jdbcTemplate.query(query, new ResultSetExtractor() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
						sb.append(rs.getObject(i));
						sb.append(",");
					}
					System.out.println(sb.toString());
				}
				return null;
			}
		});
	}
}
