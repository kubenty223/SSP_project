
from mininet.net import Mininet
from mininet.node import Controller
from mininet.cli import CLI
from mininet.log import setLogLevel, info
import time
import os

# to start: sudo python nowa_siec.py

def myNet():
	"Our second topo"

	net = Mininet(controller=Controller)

	info( '*** Adding controller\n' )
	net.addController( 'c0' )

	info( '*** Adding hosts\n' )
	h1 = net.addHost( 'h1', ip='10.0.0.1' )
	h2 = net.addHost( 'h2', ip='10.0.0.2' )
	h3 = net.addHost( 'h3', ip='10.0.0.3' )
	h4 = net.addHost( 'h4', ip='10.0.0.4' )

	info( '*** Adding servers\n' )
	srv1 = net.addHost('srv1', ip='10.0.0.101')
	srv2 = net.addHost('srv2', ip='10.0.0.102')
	srv3 = net.addHost('srv3', ip='10.0.0.103')
	srv4 = net.addHost('srv4', ip='10.0.0.104')

	info( '*** Adding switch\n' )
	s1 = net.addSwitch( 's1' )

	info( '*** Creating links\n' )
	net.addLink( h1, s1 )
	net.addLink( h2, s1 )
	net.addLink( h3, s1 )
	net.addLink( h4, s1 )

	net.addLink(srv1, s1)
	net.addLink(srv2, s1)
	net.addLink(srv3, s1)
	net.addLink(srv4, s1)

	info( '*** Starting network\n')
	net.start()
	
	info( '*** Generating 0.5 GB 1 GB 1,50 GB files\n')
	srv1.cmd("./gen_files.sh")
	

	info( '*** Starting http server\n')
	srv1.cmd( "python -m SimpleHTTPServer 6789 &")
	srv2.cmd( "python -m SimpleHTTPServer 6789 &")
	srv3.cmd( "python -m SimpleHTTPServer 6789 &")
	srv4.cmd( "python -m SimpleHTTPServer 6789 &")
 
        time.sleep(1)

        info( '*** h1-h4 sending file requests in background\n')
        h1.cmd("nohup python request_file.py 10.0.0.101:6789 h1 &")
        h2.cmd("nohup python request_file.py 10.0.0.102:6789 h2 &")
        h3.cmd("nohup python request_file.py 10.0.0.103:6789 h3 &")
        h4.cmd("nohup python request_file.py 10.0.0.104:6789 h4 &")

        info( '*** Running CLI\n' )
        CLI( net )


        info( '*** Stopping network' )
        os.system("pkill -9 'python*'")
	net.stop()

if __name__ == '__main__':
	setLogLevel( 'info' )
	myNet()
