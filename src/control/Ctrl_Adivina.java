package control;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Intento;

/**
 * Servlet implementation class Ctrl_Adivina
 */
@WebServlet("/logica")
public class Ctrl_Adivina extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private LocalDateTime tiempoInicial;
	//private LocalDateTime tiempoFinal;
	//private Integer tiempoTotal = null;

	/**
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public Ctrl_Adivina() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);

	}

	

	

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		botones(request,response);
	}
	

	
	// Mensajes de error
		protected void botones(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			HttpSession session = request.getSession(true);
			String jugar = request.getParameter("jugar");
			if (jugar != null) {
				switch (jugar) {
				case "¡JUGAR!":
					validarIntervalo(request, response);
					if (validarIntervalo(request, response) == true) {
						if (session.getAttribute("listaIntentos") != null) {
							session.setAttribute("listaIntentos", new ArrayList<Intento>());
							session.setAttribute("numero", null);
						}
						Integer num1 = (Integer) session.getAttribute("min");
						Integer num2 = (Integer) session.getAttribute("max");
						Random r = new Random();
						int numeroAleatorio = num1 + r.nextInt(num2 - num1 + 1);
						session.setAttribute("aleatorio", numeroAleatorio);
						ServletContext sc = getServletContext();
						RequestDispatcher rd = sc.getRequestDispatcher("/jugar.jsp");
						rd.forward(request, response);
					} else {
						ServletContext sc = getServletContext();
						RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
						rd.forward(request, response);
					}
					break;
				case "¡PROBAR SUERTE!":
					System.out.println(session.getAttribute("aleatorio"));
					calculando(request, response, (Integer) session.getAttribute("aleatorio"));

					ServletContext sc = getServletContext();
					RequestDispatcher rd = sc.getRequestDispatcher("/jugar.jsp");
					rd.forward(request, response);
					break;
				case "REGRESAR":
					session.invalidate();
					sc = getServletContext();
					rd = sc.getRequestDispatcher("/index.jsp");
					rd.forward(request, response);
					break;
				}
			}
			
		}

	
	
	protected boolean validarIntervalo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion = request.getSession(true);
		String num1 = request.getParameter("min");
		String num2 = request.getParameter("max");
		if (num1.equals("") || num2.equals("")) {
			String campoVacio = "No deje campos vacios";
			request.setAttribute("vacio", campoVacio);
		} else {
			try {
				Integer numero1 = Integer.parseInt(num1);
				Integer numero2 = Integer.parseInt(num2);
				if (numero1 > numero2) {
					String mayor = "Primer campo debe ser mayor que el segundo";
					request.setAttribute("mayor", mayor);
				}
			} catch (NumberFormatException e) {
				String noNumerico = "Solo numeros";
				request.setAttribute("noNumerico", noNumerico);
			}
		}

		// Si no se origina ningún error, incluye los numeros sino muestra el mensaje de
		// error

		if (request.getAttribute("mayor") == null && request.getAttribute("noNumerico") == null
				&& request.getAttribute("vacio") == null) {
			sesion.setAttribute("min", Integer.parseInt(num1));
			sesion.setAttribute("max", Integer.parseInt(num2));
			return true;
		} else {
			mensaje(request, response);
			return false;
		}

	}
	
	// Mensajes de error
		protected void mensaje(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String mensaje = "";
			if (request.getAttribute("vacio") != null) {
				mensaje = (String) request.getAttribute("vacio");
			} else if (request.getAttribute("noNumerico") != null) {
				mensaje = (String) request.getAttribute("noNumerico");
			} else if (request.getAttribute("mayor") != null) {
				mensaje = (String) request.getAttribute("mayor");
			} else if (request.getAttribute("menor") != null) {
				mensaje = (String) request.getAttribute("menor");
			}
			request.setAttribute("mensaje", mensaje);
		}

	protected void calculando(HttpServletRequest request, HttpServletResponse response, int numeroAleatorio)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		validarNumero(request, response, (Integer) session.getAttribute("min"), (Integer) session.getAttribute("max"));
		if (validarNumero(request, response, (Integer) session.getAttribute("min"),
				(Integer) session.getAttribute("max"))) {

			ArrayList<Intento> listaIntentos = (ArrayList<Intento>) session.getAttribute("listaIntentos");
			if (listaIntentos == null) {
				listaIntentos = new ArrayList<Intento>();
				session.setAttribute("listaIntentos", listaIntentos);
			}
			if (session.getAttribute("numero") != null) {
				Intento intento = new Intento();
				Integer contador =  (Integer)session.getAttribute("contador");
				if (contador == null) {
					contador  = 1;
					session.setAttribute("contador", contador);
				}
				intento.setOrden(contador);
				if (contador == 1) {
					session.setAttribute("tiempoInicial",LocalDateTime.now());
				}
				session.setAttribute("contador", ++contador);
				intento.setFechaHora(LocalDateTime.now());
				intento.setNumeroJugado((Integer) session.getAttribute("numero"));
				if (numeroAleatorio < ((Integer) session.getAttribute("numero")))
					intento.setMensaje("El número es más pequeño");
				else if (numeroAleatorio > ((Integer) session.getAttribute("numero")))
					intento.setMensaje("El número es más grande");
				else {
					intento.setMensaje("¡Has acertado!");
					session.setAttribute("tiempoFinal",LocalDateTime.now());
					LocalDateTime tiempoInicial = (LocalDateTime)session.getAttribute("tiempoInicial");
					LocalDateTime tiempoFinal = (LocalDateTime)session.getAttribute("tiempoFinal");
					int tiempoTotal = calcularTiempo(tiempoInicial, tiempoFinal);
					session.setAttribute("tiempoTotal", tiempoTotal);
				}

				listaIntentos.add(intento);
			}
		}
	}
	
	protected boolean validarNumero(HttpServletRequest request, HttpServletResponse response, int num1, int num2)
			throws ServletException, IOException {
		HttpSession sesion = request.getSession(true);
		String num = request.getParameter("numero");
		if (num.equals("")) {
			String campoVacio = "No deje campos vacios";
			request.setAttribute("vacio", campoVacio);
		} else {
			try {
				Integer numero = Integer.parseInt(num);
				if (numero > num2) {
					String mayor = "Tienes que poner un número menor respecto al intervalo máximo";
					request.setAttribute("mayor", mayor);
				} else if (numero < num1) {
					String menor = "Tienes que poner un número mayor respecto al intervalo mínimo";
					request.setAttribute("menor", menor);
				}
			} catch (NumberFormatException e) {
				String noNumerico = "Solo numeros";
				request.setAttribute("noNumerico", noNumerico);
			}
		}

		// Si no se origina ningún error, incluye los numeros sino muestra el mensaje de
		// error

		if (request.getAttribute("mayor") == null && request.getAttribute("noNumerico") == null
				&& request.getAttribute("vacio") == null && request.getAttribute("menor") == null) {
			sesion.setAttribute("numero", Integer.parseInt(num));
			return true;
		} else {
			mensaje(request, response);
			return false;
		}

	}

	public Integer calcularTiempo(LocalDateTime tiempoInicial,
			LocalDateTime tiempoFinal) {
		int hI = tiempoInicial.getHour();
		int mI = tiempoInicial.getMinute();
		int sI = tiempoInicial.getSecond();
		int segundosInicial = (hI * 3600) + (mI * 60) + sI;
		int hF = tiempoFinal.getHour();
		int mF = tiempoFinal.getMinute();
		int sF = tiempoFinal.getSecond();
		int segundosFinal = (hF * 3600) + (mF * 60) + sF;
		Integer tT = segundosFinal - segundosInicial;
		return tT;
	}
}