package com.munchymc.punishmentplugin.bukkit.commands.executor;

import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.Queue;

class QueuedCommandThread implements Runnable {
    private boolean isEnabled = false;
    private boolean cooldownOnEmpty = false;
    private int cooldownMs = 1000;
    private Queue<BukkitCommandData> executionQueue = new LinkedList<>();

    protected void executeCommand(BukkitCommandData data){
        executionQueue.add(data);
    }

    @Override
    public void run() {
        for (;isEnabled;){
            if(!executionQueue.isEmpty()){
                BukkitCommandData cmd = executionQueue.poll();
                cmd.getCommand().execute(cmd.getCommandSender(), cmd.getArgs());
                continue;
            }

            if (cooldownOnEmpty){
                try {
                    Thread.sleep(cooldownMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setColonOnEmpty(boolean cooldownOnEmpty) {
        this.cooldownOnEmpty = cooldownOnEmpty;
    }

    public void setCooldownMs(int cooldownMs) {
        this.cooldownMs = cooldownMs;
    }
}
