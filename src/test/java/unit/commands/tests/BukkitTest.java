package unit.commands.tests;

import com.munchymc.punishmentplugin.bukkit.commands.permissions.CreatePermission;
import org.junit.Assert;
import org.junit.Test;
import unit.commands.common.TestCommand;

import java.util.List;

public class BukkitTest extends TestCommand {
    @Test
    public void executePermCreate() {
        getExecutor().onCommand(getFakeSender(), null, "Punish", new String[]{"Permission", "create", "Test", "ban", "0:0:30", "test"}); //Async, outside of test!
        Assert.assertEquals("insert into munchymc_dev.actions values (test, ban, 0:0:30, test);", getDatabase().getLastSql().toLowerCase());
    }
    //User Input: /Punish Permission create Test ban 0:0:30 test
    //Example Args: [Create, Name, Action, Duration, Usage Permission

    @Test
    public void executePermCreateHelp() {
        getExecutor().onCommand(getFakeSender(), null, "Punish", new String[]{"Permission", "create", "Test", "ban", "0:0:30", "test"}); //Async, outside of test!
        Assert.assertEquals("insert into munchymc_dev.actions values (test, ban, 0:0:30, test);", getDatabase().getLastSql().toLowerCase());
    }

    @Test
    public void capTest() {
        getExecutor().onCommand(getFakeSender(), null, "PuNish", new String[]{"PerMisSion", "cReate", "hElp"}); //Async, outside of test!
        Assert.assertEquals(new CreatePermission(getDatabase()).helpString(), getFakeSender().getLastMessage());
    }
    //User Input: /Punish Permission create help
    //Example Args: create, help

    @Test
    public void executePermDelete() {
        getExecutor().onCommand(getFakeSender(), null, "Punish", new String[]{"Permission", "delete", "Test"}); //Async, outside of test!
        Assert.assertEquals("delete from munchymc_dev.actions where action_name = test;", getDatabase().getLastSql().toLowerCase());
    }
}
