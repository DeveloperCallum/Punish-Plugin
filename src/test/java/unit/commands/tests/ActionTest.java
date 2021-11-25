package unit.commands.tests;

import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.ActionParameterData;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.ActionsQuery;
import com.munchymc.punishmentplugin.common.database.SqlOperators;
import org.junit.Assert;
import org.junit.Test;
import unit.commands.common.TestCommand;
import unit.commands.common.impl.statement.FakePreparedStatement;

public class ActionTest extends TestCommand {
    @Test
    public void SqlGenerationTest() {
        ActionsQuery actionsQuery = new ActionsQuery(getDatabase(), getFakePlugin());
        ActionParameterData parameterData = actionsQuery.getParameterData();

        parameterData.getSelectData().selectName(true);
        parameterData.getWhereData().setName("bob");
        parameterData.getWhereData().setRespondingAction("BAN", SqlOperators.OR);

        //SELECT (Action_Name) FROM actions WHERE Action_Name = bob OR Responding_Action= BAN;
        Assert.assertEquals("select action_name from actions where action_name = bob or responding_action = ban;",
                ((FakePreparedStatement) actionsQuery.getParameterData().createQueryString()).getStatement().toLowerCase());
    }
}
