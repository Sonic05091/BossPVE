package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class StageArg extends Argument {

    public StageArg(BossPVE plugin, int index) {
        super(plugin, "stage", "stage <number>", index);
        setPermission("stage");
        setAlias("stage");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        //TODO allow 0 args to be passed to open gui
        if(!permissionCheck()) {
            return;
        }
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do this!");
            return;
        }
        if(args.length!=relativeIndex+1) {
            utilSender.sendMessage("%prefix% %primary%GUI support is W.I.P., please use any of the available stages for you: "+
                    getAvailableStages()+"\n"+getArgsMessage());
            return;
        }
        Stage stage = plugin.getStageManager().getStage(Integer.parseInt(args[relativeIndex]));
        if(stage==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find stage!");
            return;
        }
        if(!stage.isAllowed(utilSender.getPlayer())) {
            utilSender.sendMessage("%primary% %primary%Hey, You can't go here!");
            return;
        }
        utilSender.sendMessage("%primary% %primary%Teleporting to stage %secondary%"+stage.getLevelRequirement()+"%primary%.");
        utilSender.getPlayer().teleport(stage.getSpawn());
    }

    private String getAvailableStages() {
        int playerLevel = utilSender.getUserData().getLevel();
        List<Stage> allowedStages = plugin.getStageManager().getStages().stream()
                .filter(stage -> playerLevel>=stage.getLevelRequirement())
                .collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        boolean first=true;
        for(Stage stage : allowedStages) {
            if(!first) {
                stringBuilder.append("%primary%, ");
            } else {
                first=false;
            }
            stringBuilder.append("%secondary%").append(stage.getLevelRequirement());
        }
        return stringBuilder.toString();
    }

}