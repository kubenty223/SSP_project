package pl.edu.agh.kt;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFFlowMod;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPacketOut;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.action.OFActionSetDlDst;
import org.projectfloodlight.openflow.protocol.action.OFActionSetDlSrc;
import org.projectfloodlight.openflow.protocol.action.OFActionSetField;
import org.projectfloodlight.openflow.protocol.action.OFActionSetNwDst;
import org.projectfloodlight.openflow.protocol.action.OFActionSetNwSrc.Builder;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.OFVlanVidMatch;
import org.projectfloodlight.openflow.types.TransportPort;
import org.projectfloodlight.openflow.types.VlanVid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.packet.ARP;
import net.floodlightcontroller.packet.Data;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.TCP;
import net.floodlightcontroller.packet.UDP;

public class Flows {

	private static final Logger logger = LoggerFactory.getLogger(Flows.class);

	public static short FLOWMOD_DEFAULT_IDLE_TIMEOUT = 15;// in seconds
	public static short FLOWMOD_DEFAULT_HARD_TIMEOUT = 0; // infinite
	public static short FLOWMOD_DEFAULT_PRIORITY = 100;

	protected static boolean FLOWMOD_DEFAULT_MATCH_VLAN = true;
	protected static boolean FLOWMOD_DEFAULT_MATCH_MAC = true;
	protected static boolean FLOWMOD_DEFAULT_MATCH_IP_ADDR = true;
	protected static boolean FLOWMOD_DEFAULT_MATCH_TRANSPORT = true;

	MacAddress mac = MacAddress.BROADCAST;


	public Flows() {
		logger.info("Flows() begin/end");
	}

	public void simpleAdd(IOFSwitch sw, OFPacketIn pin, FloodlightContext cntx, List<Integer> load) {
		
		Ethernet eth = IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);

		List<OFAction> actions = new ArrayList<OFAction>();

		OFPort outPort;
		outPort = OFPort.ANY;

		if (eth.getEtherType() == EthType.ARP)
		{
			ARP arp = (ARP) eth.getPayload();
			
			IPv4Address tgtIp = arp.getTargetProtocolAddress();
			IPv4Address srcIp = arp.getSenderProtocolAddress();
		
			if (arp.getTargetHardwareAddress() != mac)	// Logowanie tylko w przypadku zapytania, nie odpowiedzi
			{
				
				logger.info("$$$$$$$$$$$$$$$$$$ ARP $$$$$$$$$$$$$$$$$");
				logger.info("$$$  SRC: {}, DST: {}  $$$", srcIp, tgtIp);
				logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			}
			
			
			if (tgtIp.toString().equals("10.0.0.1")){
				outPort = OFPort.of(1);
			}
			else if (tgtIp.toString().equals("10.0.0.2")){
				outPort = OFPort.of(2);
			}
			else if (tgtIp.toString().equals("10.0.0.3")){
				outPort = OFPort.of(3);
			}
			else if (tgtIp.toString().equals("10.0.0.4")){
				outPort = OFPort.of(4);
			}
			else if (tgtIp.toString().equals("10.0.0.101")){
				outPort = OFPort.of(5);
			}
			else if (tgtIp.toString().equals("10.0.0.102")){
				outPort = OFPort.of(6);
			}
			else if (tgtIp.toString().equals("10.0.0.103")){
				outPort = OFPort.of(7);
			}
			else if (tgtIp.toString().equals("10.0.0.104")){
				outPort = OFPort.of(8);
			}
			
			
		}
		else if (eth.getEtherType() == EthType.IPv4){
			IPv4Address dstIp = IPv4Address.of("10.0.0.255");
			IPv4 ip = (IPv4) eth.getPayload();

			
			if(pin.getInPort().getPortNumber() < 5)	// Czy pakiet idzie od hosta
			{	
				MacAddress dstMac = MacAddress.BROADCAST;
				
				final List<Integer> currLoad = new ArrayList<>(load);
				int minLoad = Collections.min(currLoad);
				String minLoadStr = String.format("%02d", minLoad);

				logger.info("##############################################################");
				logger.info("#############         CLIENT REQUEST         #################");
				
				if (minLoad == currLoad.get(0)){
					logger.info("#####         WYBRANO SERWER 1 OBCIAZENIE: {} %          #####", minLoadStr);
					outPort = OFPort.of(5);
					dstIp = IPv4Address.of("10.0.0.101");
					dstMac = MacAddress.of("00:00:00:00:00:10");
				}
				else if (minLoad == currLoad.get(1)){
					logger.info("#####         WYBRANO SERWER 2 OBCIAZENIE: {} %          #####", minLoadStr);
					outPort = OFPort.of(6);
					dstIp = IPv4Address.of("10.0.0.102");
					dstMac = MacAddress.of("00:00:00:00:00:20");
				}
				else if (minLoad == currLoad.get(2)){
					logger.info("#####         WYBRANO SERWER 3 OBCIAZENIE: {} %          #####", minLoadStr);
					outPort = OFPort.of(7);
					dstIp = IPv4Address.of("10.0.0.103");
					dstMac = MacAddress.of("00:00:00:00:00:30");
				}
				else if (minLoad == currLoad.get(3)){
					logger.info("#####         WYBRANO SERWER 4 OBCIAZENIE: {} %          #####", minLoadStr);
					outPort = OFPort.of(8);
					dstIp = IPv4Address.of("10.0.0.104");
					dstMac = MacAddress.of("00:00:00:00:00:40");
					
				}
				
				logger.info("#####     HOST IP: {}, SERVER IP: {}       #####", ip.getSourceAddress(), dstIp);
				logger.info("##############################################################");
				
				OFActionSetDlDst.Builder setDstMac = sw.getOFFactory().actions().buildSetDlDst();
				setDstMac.setDlAddr(dstMac);
				actions.add(setDstMac.build());
				
				OFActionSetNwDst.Builder actionChangeIpAddDst = sw.getOFFactory().actions().buildSetNwDst();
				actionChangeIpAddDst.setNwAddr(dstIp);
				actions.add(actionChangeIpAddDst.build());

			}
			else // pakiet idzie od serwera
			{
				logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				logger.info("%%%%%%%%%%%%%        SERVER RESPONSE         %%%%%%%%%%%%%%%%%");
				logger.info("%%%%%%%%%%%%%     SERVER IP: {}        %%%%%%%%%%%%%%%", ip.getSourceAddress().toString());
				if (ip.getDestinationAddress().toString().equals("10.0.0.1")){
					logger.info("%%%%%%%%%%%%%  ZWROT DO HOSTA 1 IP: {}   %%%%%%%%%%%%%%%", ip.getDestinationAddress().toString());
					outPort = OFPort.of(1);
				}
				else if (ip.getDestinationAddress().toString().equals("10.0.0.2")){
					logger.info("%%%%%%%%%%%%%  ZWROT DO HOSTA 2 IP: {}   %%%%%%%%%%%%%%%", ip.getDestinationAddress().toString());
					outPort = OFPort.of(2);
				}
				else if (ip.getDestinationAddress().toString().equals("10.0.0.3")){
					logger.info("%%%%%%%%%%%%%  ZWROT DO HOSTA 3 IP: {}   %%%%%%%%%%%%%%%", ip.getDestinationAddress().toString());
					outPort = OFPort.of(3);
				}
				else if (ip.getDestinationAddress().toString().equals("10.0.0.4")){
					logger.info("%%%%%%%%%%%%%  ZWROT DO HOSTA 4 IP: {}   %%%%%%%%%%%%%%%", ip.getDestinationAddress().toString());
					outPort = OFPort.of(4);
				}
				
				logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				

				IPv4Address srcIp = IPv4Address.of("10.0.0.101");
				Builder actionChangeIpAddSrc = sw.getOFFactory().actions().buildSetNwSrc();
				actionChangeIpAddSrc.setNwAddr(srcIp);
				actions.add(actionChangeIpAddSrc.build());
			}
			
		}
		
		// actions

		// Set source/dst mac add
		OFActionOutput.Builder actionOutPort = sw.getOFFactory().actions().buildOutput();
		actionOutPort.setPort(outPort);
		actionOutPort.setMaxLen(Integer.MAX_VALUE);
		actions.add(actionOutPort.build());

		// FlowModBuilder
		OFFlowMod.Builder fmb = sw.getOFFactory().buildFlowAdd();
		// match
		Match m = createMatchFromPacket(sw, pin.getInPort(), cntx);
				
		fmb.setMatch(m).setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT).setHardTimeout(FLOWMOD_DEFAULT_HARD_TIMEOUT)
				.setBufferId(pin.getBufferId()).setOutPort(outPort).setPriority(FLOWMOD_DEFAULT_PRIORITY);
		fmb.setActions(actions);

		// write flow to switch
		try {
			sw.write(fmb.build());
			logger.info("Flow from port {} forwarded to port {}; match: {}",
					new Object[] { pin.getInPort().getPortNumber(), outPort.getPortNumber(), m.toString() });
		} catch (Exception e) {
			logger.error("error {}", e);
		}
	}

	public static Match createMatchFromPacket(IOFSwitch sw, OFPort inPort, FloodlightContext cntx) {
		// The packet in match will only contain the port number.
		// We need to add in specifics for the hosts we're routing between.
		Ethernet eth = IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		VlanVid vlan = VlanVid.ofVlan(eth.getVlanID());
		MacAddress srcMac = eth.getSourceMACAddress();
		MacAddress dstMac = eth.getDestinationMACAddress();

		Match.Builder mb = sw.getOFFactory().buildMatch();
		mb.setExact(MatchField.IN_PORT, inPort);

		if (FLOWMOD_DEFAULT_MATCH_MAC) {
			mb.setExact(MatchField.ETH_SRC, srcMac).setExact(MatchField.ETH_DST, dstMac);
		}

		if (FLOWMOD_DEFAULT_MATCH_VLAN) {
			if (!vlan.equals(VlanVid.ZERO)) {
				mb.setExact(MatchField.VLAN_VID, OFVlanVidMatch.ofVlanVid(vlan));
			}
		}

		// TODO Detect switch type and match to create hardware-implemented flow
		if (eth.getEtherType() == EthType.IPv4) { /*
													 * shallow check for equality is okay for EthType
													 */
			IPv4 ip = (IPv4) eth.getPayload();
			IPv4Address srcIp = ip.getSourceAddress();
			IPv4Address dstIp = ip.getDestinationAddress();

			if (FLOWMOD_DEFAULT_MATCH_IP_ADDR) {
				mb.setExact(MatchField.ETH_TYPE, EthType.IPv4).setExact(MatchField.IPV4_SRC, srcIp)
						.setExact(MatchField.IPV4_DST, dstIp);
			}

			if (FLOWMOD_DEFAULT_MATCH_TRANSPORT) {
				/*
				 * Take care of the ethertype if not included earlier, since it's a prerequisite
				 * for transport ports.
				 */
				if (!FLOWMOD_DEFAULT_MATCH_IP_ADDR) {
					mb.setExact(MatchField.ETH_TYPE, EthType.IPv4);
				}

				if (ip.getProtocol().equals(IpProtocol.TCP)) {
					TCP tcp = (TCP) ip.getPayload();
					mb.setExact(MatchField.IP_PROTO, IpProtocol.TCP).setExact(MatchField.TCP_SRC, tcp.getSourcePort())
							.setExact(MatchField.TCP_DST, tcp.getDestinationPort());
				} else if (ip.getProtocol().equals(IpProtocol.UDP)) {
					UDP udp = (UDP) ip.getPayload();
					mb.setExact(MatchField.IP_PROTO, IpProtocol.UDP).setExact(MatchField.UDP_SRC, udp.getSourcePort())
							.setExact(MatchField.UDP_DST, udp.getDestinationPort());
				}
			}
		} if (eth.getEtherType() == EthType.ARP) {
			
	        mb.setExact(MatchField.ETH_TYPE, EthType.ARP);

	        ARP arp = (ARP) eth.getPayload();
	        IPv4Address srcIp = arp.getSenderProtocolAddress();
	        IPv4Address dstIp = arp.getTargetProtocolAddress();

	        if (FLOWMOD_DEFAULT_MATCH_IP_ADDR) {
	            mb.setExact(MatchField.ARP_SPA, srcIp).setExact(MatchField.ARP_TPA, dstIp);
	        }
	    }

		return mb.build();
	}
}
