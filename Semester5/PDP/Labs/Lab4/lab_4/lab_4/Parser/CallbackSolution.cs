using System.Collections.Generic;
using System.Threading;
using Lab_4.Socket;

namespace Lab_4.Parser
{
    internal class CallbackSolution : Common
    {
        protected override string ParserType => "Callback";

        public CallbackSolution(List<string> urls) : base(urls)
        {
        }

        protected override void Run()
        {
            ForEach((index, url) => Start(SocketHandler.Create(url, index)));
        }

        private void Start(SocketHandler socket)
        {
            if (socket == null)
            {
                Console.WriteLine("Socket is null.");
                return;
            }

            socket.BeginConnect(HandleConnected);

            while (!socket.Connected)
            {
                Task.Delay(100).Wait();
            }
        }
        
        private void HandleConnected(SocketHandler socket)
        {
            LogConnected(socket);
            socket.BeginSend(HandleSent);
        }

        private void HandleSent(SocketHandler socket, int numberOfSentBytes)
        {
            LogSent(socket, numberOfSentBytes);
            socket.BeginReceive(HandleReceived);
        }

        private void HandleReceived(SocketHandler socket)
        {
            LogReceived(socket);
            socket.ShutdownAndClose();
        }
    }
}
