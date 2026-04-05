package com.l2p.hmps.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotResponse {

    private LocalTime time;

    private Boolean isAvailable;
}
