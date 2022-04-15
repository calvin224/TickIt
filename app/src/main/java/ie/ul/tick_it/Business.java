package ie.ul.tick_it;

import androidx.annotation.NonNull;

public class Business {
    private String Name;
    private String Location;
    private String Image;
    private String Type;
    private String Index;

    public Business() {
    }

    public Business(String Name,String Location,String Image,String Type,String Index) {
        this.Name = Name;
        this.Location = Location;
        this.Image=Image;
        this.Type =Type;
        this.Index=Index;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }
    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
    public String getType() {
        return Type;
    }
    public String getIndex() {
        return Index;
    }

    @NonNull
    @Override
    public String toString() {
        return this.Name + " " + this.Location + " " + this.Image;
    }
}
