package admin.panel.dto.photogallery;

import lombok.Data;

import java.util.List;

@Data
public class GalleryCategoryResponse {
    private Long id;
    private String title;
    private String coverImage;
    private List<String> images;
    private Integer displayOrder;
}