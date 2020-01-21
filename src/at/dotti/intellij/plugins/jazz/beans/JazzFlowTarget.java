package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JazzFlowTarget extends JazzBase {

    @JsonProperty("incoming-flow")
    private JazzIncomingFlow incomingFlow;
    @JsonProperty("outgoing-flow")
    private JazzOutgoingFlow outgoingFlow;
    @JsonProperty
    private String type;

}
