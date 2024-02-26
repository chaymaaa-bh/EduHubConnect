package models;

public class Module {
    private int module_id,level_id;
    private String module_name;

    public Module() {
    }

    public Module(int level_id, String module_name) {
        this.level_id = level_id;
        this.module_name = module_name;
    }

    public Module(int module_id, int level_id, String module_name) {
        this.module_id = module_id;
        this.level_id = level_id;
        this.module_name = module_name;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    @Override
    public String toString() {
        return "modules{" +
                "module_id=" + module_id +
                ", level_id=" + level_id +
                ", module_name='" + module_name + '\'' +
                '}';
    }
}
