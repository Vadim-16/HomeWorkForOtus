package ca.study.purpose.Executor;


import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MyDbExecutorImpl<T> implements DbExecutor<T> {
    private final Connection connection;

    public MyDbExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long executeInsert(String sql, List<Object> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParams(pst, params);
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            this.connection.rollback(savePoint);
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void executeUpdate(String sql, List<Object> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS)) {
            setParams(pst, params);
            pst.executeUpdate();
        } catch (SQLException e) {
            this.connection.rollback(savePoint);
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }

    private void setParams(PreparedStatement pst, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object parameter = params.get(i);
            if (parameter instanceof String) {
                pst.setString(i + 1, (String) parameter);
            } else if (parameter instanceof Byte) {
                pst.setByte(i + 1, (byte) parameter);
            } else if (parameter instanceof Short) {
                pst.setShort(i + 1, (short) parameter);
            } else if (parameter instanceof Integer) {
                pst.setInt(i + 1, (int) parameter);
            } else if (parameter instanceof Long) {
                pst.setLong(i + 1, (long) parameter);
            } else if (parameter instanceof BigDecimal) {
                pst.setBigDecimal(i + 1, (BigDecimal) parameter);
            } else if (parameter instanceof Double) {
                pst.setDouble(i + 1, (double) parameter);
            } else if (parameter instanceof Float) {
                pst.setFloat(i + 1, (float) parameter);
            } else if (parameter instanceof Boolean) {
                pst.setBoolean(i + 1, (boolean) parameter);
            }
        }
    }
}
