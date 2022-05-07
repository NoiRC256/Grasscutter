package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.server.packet.recv.HandlerEnterWorldAreaReq;
import emu.grasscutter.server.packet.send.PacketSceneAreaWeatherNotify;

import java.util.List;

@Command(label = "weather", usage = "weather <weatherId> [climateId]",
        description = "Changes the weather.", aliases = {"w"}, permission = "player.weather")
public final class WeatherCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Weather_usage);
            return;
        }

        // Debug: enter AreaID2 get ID, use ID calculate AreaID get WeatherAreaID;
        int AreaID2 = Integer.parseInt(args.get(0));
        int ID = GameData.getAreaIdByWorldAreaId2(AreaID2);
        int AreaID = HandlerEnterWorldAreaReq.CalculateAreaId(ID);
        int WeatherAreaID = GameData.getWeatherAreaIdByAreaId(AreaID);
        String weatherAreaDebugMsg = String.format("AreaID2: %d, corresponding ID: %d\n" +
        "AreaID: %d, corresponding WeatherAreaID: %d", AreaID2, ID, AreaID, WeatherAreaID);
        CommandHandler.sendMessage(sender, weatherAreaDebugMsg);

        try {
            int weatherId = Integer.parseInt(args.get(0));
            int climateId = args.size() > 1 ? Integer.parseInt(args.get(1)) : 1;

            ClimateType climate = ClimateType.getTypeByValue(climateId);

            sender.getScene().setWeather(weatherId);
            sender.getScene().setClimate(climate);
            sender.getScene().broadcastPacket(new PacketSceneAreaWeatherNotify(sender));
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage()
                    .Weather_message.replace("{weatherId}", Integer.toString(weatherId))
                    .replace("{climateId}", Integer.toString(climateId)));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Weather_invalid_id);
        }
    }
}
