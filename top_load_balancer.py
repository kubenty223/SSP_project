from mininet.topo import Topo
from mininet.cli import CLI

class MyTopo(Topo):
        "Our test topology."

        def __init__(self):
                "Create custom topo."

                Topo.__init__(self)

                #Add hosts
                h1 = self.addHost('h1')
                h2 = self.addHost('h2')
                h3 = self.addHost('h3')
                h4 = self.addHost('h4')

                #Add servers
                srv1 = self.addHost('srv1')
                srv2 = self.addHost('srv2')
                srv3 = self.addHost('srv3')
                srv4 = self.addHost('srv4')

                #Add switch
                s1 = self.addSwitch('s1')

                #Add links
                self.addLink( h1, s1)
                self.addLink( h2, s1)
                self.addLink( h3, s1)
                self.addLink( h4, s1)


                self.addLink(srv1, s1)
                self.addLink(srv2, s1)
                self.addLink(srv3, s1)
                self.addLink(srv4, s1)

		#print(self.g.h1.__dict__)
		#host1 = 
		#print(self.g.nodes.__dict__) #get('h1') 
		#self.build()
		#CLI(self)
		#host1.net.cmd('pingall')
		#host1 = Topo.getHost('h1') 
		#host1.cmd('./first_ping.sh')	
		#self.h1Pingh2()

	def h1Pingh2(self):
		h1.cli("echo halo")

topos = { 'mytopo': ( lambda: MyTopo() ) }
