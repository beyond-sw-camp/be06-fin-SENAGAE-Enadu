package org.example.backend.ErrorArchive.Model.Res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToggleResponse {
    private Boolean result;
    public ToggleResponse(Boolean result){
        this.result = result;
    }
}
