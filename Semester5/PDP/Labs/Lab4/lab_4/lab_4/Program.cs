using Lab_4.Parser;

namespace Lab_4
{
    internal class Program
    {
        private static readonly List<string> Urls = new()
        {
            "ajkajajksjksjkaasssss.com",
            "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-4-futures-continuations.html",
            "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lecture-8-parallel-advanced-recursive.html"
        };

        static void Main()
        {
            Console.WriteLine("1. Callback Parser");
            Console.WriteLine("2. Task Parser");
            Console.WriteLine("3. Async Await Parser");
            string choice = Console.ReadLine();
            switch (choice)
            {
                case "1":
                    var callbackSolution = new CallbackSolution(Urls);
                    break;
                case "2":
                    var taskSolution = new TaskSolution(Urls);
                    break;
                case "3":
                    var asyncAwaitSolution = new AsyncAwaitSolution(Urls);
                    break;
                default:
                    Console.WriteLine("Invalid choice");
                    break;
            }
        }
    }
}