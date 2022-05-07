package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterWorldAreaReqOuterClass.EnterWorldAreaReq;
import emu.grasscutter.net.proto.PacketHeadOuterClass.PacketHead;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEnterWorldAreaRsp;
import emu.grasscutter.server.packet.send.PacketSceneAreaWeatherNotify;

@Opcodes(PacketOpcodes.EnterWorldAreaReq)
public class HandlerEnterWorldAreaReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PacketHead head = PacketHead.parseFrom(header);
        EnterWorldAreaReq req = EnterWorldAreaReq.parseFrom(payload);

        int worldAreaID = GameData.getAreaIdByWorldAreaId2(req.getAreaId());
        int areaID = CalculateAreaId(worldAreaID);
        int weatherAreaID = GameData.getWeatherAreaIdByAreaId(areaID);
        ClimateType climate = ClimateType.getTypeByValue(1);

        String enterAreaWeatherMsg = String.format("Entered area with AreaID2: %d, ID: %d\n" +
                        "Calculated AreaID: %d, corresponding WeatherAreaID: %d",
                req.getAreaId(), worldAreaID, areaID, weatherAreaID);

        CommandHandler.sendMessage(session.getPlayer(), enterAreaWeatherMsg);
        session.getPlayer().getScene().setWeather(weatherAreaID);
        session.getPlayer().getScene().setClimate(climate);
        session.getPlayer().getScene().broadcastPacket(new PacketSceneAreaWeatherNotify(session.getPlayer()));

        session.send(new PacketEnterWorldAreaRsp(head.getClientSequenceId(), req));
        //session.send(new PacketScenePlayerLocationNotify(session.getPlayer()));

    }

    // Get WeatherConfig AreaID from WorldAreaConfig ID.
    public static int CalculateAreaId(int worldAreaId) {
        int r1 = worldAreaId % 1000;
        int r2 = r1 - r1 % 10;
        int result = worldAreaId - r2;
        r2 /= 10;
        return result += r2 + 20;
    }

}
