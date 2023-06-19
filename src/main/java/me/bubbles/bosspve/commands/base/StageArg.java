package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            utilSender.sendMessage("%prefix% %primary%Available stages for you: "+
                    getAvailableStages()+"\n"+getArgsMessage());
            return;
        }
        int stageNum;
        try {
            stageNum=Integer.parseInt(args[relativeIndex]);
        } catch(NumberFormatException e) {
            utilSender.sendMessage("%prefix% %primary%Could not find stage!");
            return;
        }
        Stage stage = plugin.getStageManager().getStage(stageNum);
        if(stage==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find stage!");
            return;
        }
        if(!stage.isAllowed(utilSender.getPlayer())) {
            utilSender.sendMessage("%prefix% %primary%Hey, you can't go there!");
            return;
        }
        utilSender.sendMessage("%prefix% %primary%Teleporting to stage %secondary%"+stage.getLevelRequirement()+"%primary%.");
        utilSender.getPlayer().teleport(stage.getSpawn());
    }

    private String getAvailableStages() {
        int playerLevel = utilSender.getUserData().getLevel();
        List<Integer> allowedStages = new ArrayList<>();
        plugin.getStageManager().getStages().stream()
                .filter(stage -> playerLevel>=stage.getLevelRequirement())
                .forEach(stage -> allowedStages.add(stage.getLevelRequirement())
                );
        Collections.sort(allowedStages);
        StringBuilder stringBuilder = new StringBuilder();
        boolean first=true;
        for(Integer stage : allowedStages) {
            if(!first) {
                stringBuilder.append("%primary%, ");
            } else {
                first=false;
            }
            stringBuilder.append("%secondary%").append(stage);
        }
        return stringBuilder.toString();
    }

}