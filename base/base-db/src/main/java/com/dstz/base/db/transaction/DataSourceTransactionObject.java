package com.dstz.base.db.transaction;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronizationUtils;

/**
 * <pre>
 * DataSource transaction object, representing a ConnectionHolder.
 * Used as transaction object by DataSourceTransactionManager.
 * 直接copy了DataSourceTransactionManager的内部类
 * </pre>
 */
public class DataSourceTransactionObject extends JdbcTransactionObjectSupport {
	private boolean newConnectionHolder;

	private boolean mustRestoreAutoCommit;

	public void setConnectionHolder(ConnectionHolder connectionHolder, boolean newConnectionHolder) {
		super.setConnectionHolder(connectionHolder);
		this.newConnectionHolder = newConnectionHolder;
	}

	public boolean isNewConnectionHolder() {
		return this.newConnectionHolder;
	}

	public void setMustRestoreAutoCommit(boolean mustRestoreAutoCommit) {
		this.mustRestoreAutoCommit = mustRestoreAutoCommit;
	}

	public boolean isMustRestoreAutoCommit() {
		return this.mustRestoreAutoCommit;
	}

	public void setRollbackOnly() {
		getConnectionHolder().setRollbackOnly();
	}

	@Override
	public boolean isRollbackOnly() {
		return getConnectionHolder().isRollbackOnly();
	}

	@Override
	public void flush() {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			TransactionSynchronizationUtils.triggerFlush();
		}
	}
}
