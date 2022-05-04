package net.sopod.soim.router.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * RegistryRes
 *
 * @author tmy
 * @date 2022-05-04 16:05
 */
@Data
@Accessors(chain = true)
public class RegistryRes implements Serializable {

    private static final long serialVersionUID = -4367288919640496421L;

    private Boolean success;

    private String imRouterId;

}
