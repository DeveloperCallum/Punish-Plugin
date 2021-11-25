package unit.commands.bukkit;

@Deprecated
public class FakePlayerWithName extends FakePlayer {
    private final String name;
    private final String displayName;

    public FakePlayerWithName(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getName() {
        return name;
    }
}
