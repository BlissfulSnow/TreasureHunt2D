package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Obj_OpenedChest extends SuperObject{

    public Obj_OpenedChest() {
        name = "OpenedChest";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest_opened.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
