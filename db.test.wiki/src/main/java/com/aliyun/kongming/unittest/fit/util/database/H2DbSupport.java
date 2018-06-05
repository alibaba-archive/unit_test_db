package com.aliyun.kongming.unittest.fit.util.database;

import java.util.Set;

import org.unitils.core.dbsupport.DbSupport;

public class H2DbSupport extends DbSupport {
    /**
     * Creates support for HsqlDb databases.
     */
    public H2DbSupport() {
        super("h2db");
    }

    @Override
    public Set<String> getColumnNames(String tableName) {
        return getSQLHandler()
                .getItemsAsStringSet("select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = '"
                        + tableName + "' AND TABLE_SCHEMA = '" + getSchemaName() + "'");
    }

    @Override
    public Set<String> getTableNames() {
        return getSQLHandler().getItemsAsStringSet(
                "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_TYPE = 'TABLE' AND TABLE_SCHEMA = '"
                        + getSchemaName() + "'");
    }

    @Override
    public Set<String> getViewNames() {
        return getSQLHandler().getItemsAsStringSet(
                "select TABLE_NAME from INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE = 'VIEW' AND TABLE_SCHEMA = '"
                        + getSchemaName() + "'");
    }

    @Override
    public Set<String> getSequenceNames() {
        return getSQLHandler().getItemsAsStringSet(
                "select SEQUENCE_NAME from INFORMATION_SCHEMA.SYSTEM_SEQUENCES where SEQUENCE_SCHEMA = '"
                        + getSchemaName() + "'");
    }

    @Override
    public Set<String> getTriggerNames() {
        return getSQLHandler().getItemsAsStringSet(
                "select TRIGGER_NAME from INFORMATION_SCHEMA.SYSTEM_TRIGGERS where TRIGGER_SCHEM = '" + getSchemaName()
                        + "'");
    }

    @Override
    public long getSequenceValue(String sequenceName) {
        return getSQLHandler()
                .getItemAsLong("select START_WITH from INFORMATION_SCHEMA.SYSTEM_SEQUENCES where SEQUENCE_SCHEMA = '"
                        + getSchemaName() + "' and SEQUENCE_NAME = '" + sequenceName + "'");
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public boolean supportsTriggers() {
        return true;
    }

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public void incrementSequenceToValue(String sequenceName, long newSequenceValue) {
        getSQLHandler()
                .executeUpdate("alter sequence " + qualified(sequenceName) + " restart with " + newSequenceValue);
    }

    @Override
    public void incrementIdentityColumnToValue(String tableName, String identityColumnName, long identityValue) {
        getSQLHandler().executeUpdate("alter table " + qualified(tableName) + " alter column "
                + quoted(identityColumnName) + " RESTART WITH " + identityValue);
    }

    @Override
    public void disableReferentialConstraints() {
        Set<String> tableNames = getTableNames();
        for (String tableName : tableNames) {
            disableReferentialConstraints(tableName);
        }
    }

    @Override
    public void disableValueConstraints() {
        Set<String> tableNames = getTableNames();
        for (String tableName : tableNames) {
            disableValueConstraints(tableName);
        }
    }

    private void disableReferentialConstraints(String tableName) {
        Set<String> constraintNames = this.getForeignKeyConstraintNames(tableName);
        for (String constraintName : constraintNames) {
            this.removeForeignKeyConstraint(tableName, constraintName);
        }
    }

    private void disableValueConstraints(String tableName) {
        Set<String> primaryKeyColumnNames = this.getPrimaryKeyColumnNames(tableName);

        Set<String> notNullColumnNames = this.getNotNullColummnNames(tableName);
        for (String notNullColumnName : notNullColumnNames) {
            if (primaryKeyColumnNames.contains(notNullColumnName)) {
                continue;
            }
            this.removeNotNullConstraint(tableName, notNullColumnName);
        }
    }

    private Set<String> getPrimaryKeyColumnNames(String tableName) {
        return getSQLHandler().getItemsAsStringSet(
                "select COLUMN_NAME from INFORMATION_SCHEMA.INDEXES where PRIMARY_KEY=TRUE AND TABLE_NAME = '"
                        + tableName + "' AND TABLE_SCHEMA = '" + getSchemaName() + "'");
    }

    private Set<String> getNotNullColummnNames(String tableName) {
        return getSQLHandler().getItemsAsStringSet(
                "select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where IS_NULLABLE = 'NO' AND TABLE_NAME = '"
                        + tableName + "' AND TABLE_SCHEMA = '" + getSchemaName() + "'");
    }

    private Set<String> getForeignKeyConstraintNames(String tableName) {
        return getSQLHandler().getItemsAsStringSet("select CONSTRAINT_NAME from INFORMATION_SCHEMA.CONSTRAINTS "
                + "where CONSTRAINT_TYPE = 'REFERENTIAL' AND TABLE_NAME = '" + tableName + "' AND CONSTRAINT_SCHEMA = '"
                + getSchemaName() + "'");
    }

    private void removeForeignKeyConstraint(String tableName, String constraintName) {
        getSQLHandler()
                .executeUpdate("alter table " + qualified(tableName) + " drop constraint " + quoted(constraintName));
    }

    private void removeNotNullConstraint(String tableName, String columnName) {
        getSQLHandler().executeUpdate(
                "alter table " + qualified(tableName) + " alter column " + quoted(columnName) + " set null");
    }
}
