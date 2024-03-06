using System.Collections.Generic;
using System.Threading.Tasks;
using Lab_4.Socket;

namespace Lab_4.Parser
{
    internal class TaskSolution : Common
    {
        protected override string ParserType => "Task";

        public TaskSolution(List<string> urls) : base(urls)
        {
        }

        protected override void Run()
        {
            var tasks = Map((index, url) => Task.Run(() =>
                Start(SocketHandler.Create(url, index))));

            Task.WhenAll(tasks).Wait();
        }

        private Task Start(SocketHandler socket)
        {
            if (socket == null)
            {
                // Handle the case where socket is null
                Console.WriteLine("Socket is null.");
                return Task.CompletedTask;
            }

            socket.BeginConnectAsync().Wait();
            LogConnected(socket);

            var sendTask = socket.BeginSendAsync();
            sendTask.Wait();
            var numberOfSentBytes = sendTask.Result;
            LogSent(socket, numberOfSentBytes);

            socket.BeginReceiveAsync().Wait();
            LogReceived(socket);

            socket.ShutdownAndClose();
            return Task.CompletedTask;
        }

    }
}
