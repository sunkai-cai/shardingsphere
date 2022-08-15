package org.apache.shardingsphere.dbdiscovery.distsql.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import lombok.SneakyThrows;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.apache.shardingsphere.dbdiscovery.distsql.parser.facade.DatabaseDiscoveryDistSQLStatementParserFacade;
import org.apache.shardingsphere.dbdiscovery.distsql.parser.statement.CreateDatabaseDiscoveryRuleStatement;
import org.apache.shardingsphere.distsql.parser.core.featured.FeaturedDistSQLStatementParserFacadeFactory;
import org.apache.shardingsphere.distsql.parser.statement.DistSQLStatement;
import org.apache.shardingsphere.sql.parser.api.visitor.SQLVisitor;
import org.apache.shardingsphere.sql.parser.core.ParseASTNode;
import org.apache.shardingsphere.sql.parser.core.SQLParserFactory;
import org.junit.Test;

public class DatabaseDiscoveryDistSQLTest {

    @Test
    public void assertCreateDatabaseDiscoveryRule() {
        String sql = "CREATE DB_DISCOVERY RULE db_discovery_group_0 ("
                + "RESOURCES(ds_0, ds_1), TYPE(NAME=mgr,PROPERTIES('group-name'='92504d5b')),"
                + "HEARTBEAT(PROPERTIES('keep-alive-cron'='0/5 * * * * ?')))";

        CreateDatabaseDiscoveryRuleStatement distSQLStatement = (CreateDatabaseDiscoveryRuleStatement) getDistSQLStatement(sql);
        assertThat(distSQLStatement.getRules().size(), is(1));
    }

    @SneakyThrows(ReflectiveOperationException.class)
    @SuppressWarnings("rawtypes")
    private DistSQLStatement getDistSQLStatement(final String sql) {
        DatabaseDiscoveryDistSQLStatementParserFacade facade = new DatabaseDiscoveryDistSQLStatementParserFacade();
        ParseASTNode parseASTNode = (ParseASTNode) SQLParserFactory.newInstance(sql, facade.getLexerClass(), facade.getParserClass()).parse();
        SQLVisitor visitor = FeaturedDistSQLStatementParserFacadeFactory.getInstance(facade.getType()).getVisitorClass().getDeclaredConstructor().newInstance();
        return (DistSQLStatement) ((ParseTreeVisitor) visitor).visit(parseASTNode.getRootNode());
    }
}
