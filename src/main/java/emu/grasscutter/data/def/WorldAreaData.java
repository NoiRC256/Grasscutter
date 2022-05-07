package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "WorldAreaConfigData.json")
public class WorldAreaData extends GameResource {
    private int Id;

    private int ID;
    private int SceneID;
    private String AreaType;
    private int AreaID1;
    private int AreaID2;
    private int AreaNameTextMapHash;
    private String TerrainType;
    private boolean ShowTips;
    private float MinimapScale;

    @Override
    public int getId() {
        return Id;
    }

    public int getID(){
        return this.ID;
    }

    public int getSceneID() {
        return SceneID;
    }

    public String getAreaType() {
        return AreaType;
    }

    public int getAreaID1() {
        return AreaID1;
    }

    public int getAreaID2() {
        return AreaID2;
    }

    public int getAreaNameTextMapHash() {
        return AreaNameTextMapHash;
    }

    public String getTerrainType() {
        return TerrainType;
    }

    public boolean isShowTips() {
        return ShowTips;
    }

    public float getMinimapScale() {
        return MinimapScale;
    }

    @Override
    public void onLoad() {
        int temp = ID;
        ID = AreaID2;
        AreaID2 = temp;
    }
}
