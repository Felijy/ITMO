package web.lab.web41;

import lombok.Getter;
import lombok.Setter;

public class PointRequest {
    @Getter @Setter
    private Double x;
    @Getter @Setter
    private Double y;
    @Getter @Setter
    private Double r;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
}
