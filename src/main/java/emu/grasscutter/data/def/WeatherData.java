package emu.grasscutter.data.def;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;

import java.awt.geom.Area;

@ResourceType(name = "WeatherExcelConfigData.json")
public class WeatherData extends GameResource {
    private int Id;

    private int AreaID;
    private int WeatherAreaID;
    private String MaxHeightStr;
    private boolean IsUseDefault;
    private String TemplateName;
    private int Priority;
    private String ProfileName;
    private String DefaultClimate;
    private int SceneID;

    @Override
    public int getId() {
        return Id;
    }
    public int getAreaID(){
        return AreaID;
    }
    public int getWeatherAreaID(){
        return WeatherAreaID;
    }

    public String getMaxHeightStr() {
        return MaxHeightStr;
    }

    public boolean isUseDefault() {
        return IsUseDefault;
    }

    public String getTemplateName() {
        return TemplateName;
    }

    public int getPriority() {
        return Priority;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public String getDefaultClimate() {
        return DefaultClimate;
    }

    public int getSceneID() {
        return SceneID;
    }

    @Override
    public void onLoad() {
    }
}
