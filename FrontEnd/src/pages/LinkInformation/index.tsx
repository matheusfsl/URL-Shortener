import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import zod from "zod";
import { useState } from "react";
import DashboardModal from "./DashboardModal";
import { getUrlInformation } from "../../core/services";
import type { UrlServicesModel } from "../../core/services/models/UrlServicesModel";

export default function LinkInformation() {
  const navigate = useNavigate();

  const [isOpenModal, setIsOpenModal] = useState<boolean>(false);
  const [dashboardInfo, setDashboardInfo] = useState<UrlServicesModel.GetUrlInformations.Response|undefined>(undefined)

  const schema = zod.object({
    urlShort: zod
      .string()
      .nonempty({ error: "Informe uma URL encurtada" })
      .url({ protocol: /^https?$/, error: "URL inválida" }),
  });
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ resolver: zodResolver(schema), reValidateMode: "onSubmit" });

  const onSubmit = handleSubmit(async (data) => await getUrlInformation(data.urlShort).then(response=>{
    console.log(response)
    setDashboardInfo(response)
    setIsOpenModal(true)
  }).catch(error=>console.log('ERRO----',error)));
  //const openModalTest = () => setIsOpenModal(true);
  const onCloseModal = () => setIsOpenModal(false);
  const goToHome = () => navigate("/");
  return (
    <div className="min-h-screen flex items-center justify-center bg-[linear-gradient(135deg,#f7f8fc,#e9ebf3)] px-4 font-sans">
      <div className="bg-white rounded-2xl shadow-lg max-w-5xl w-full p-12 md:flex md:items-center md:justify-between transition-shadow">
        <div className="md:flex-1">
          <h1 className="text-3xl text-blue-600 font-semibold mb-3">
            Ver engajamento
          </h1>
          <p className="text-gray-600 mb-6">
            Digite seu link encurtado para visualizar estatísticas de acesso.
          </p>

          <form
            onSubmit={onSubmit}
            className="bg-gray-100 rounded-lg p-2 flex items-center max-w-md"
          >
            <input
              {...register("urlShort")}
              placeholder="Insira uma URL curta"
              className="flex-1 bg-transparent outline-none px-3 py-2 text-gray-800"
            />
            <button
              className="ml-3 bg-blue-600 hover:bg-blue-700 text-white rounded-lg px-4 py-2 font-medium"
            >
              Consultar
            </button>
          </form>
          {errors.urlShort && (
            <span className="text-error font-medium">
              {errors.urlShort.message}
            </span>
          )}
          <div className="flex justify-start mt-3">
            <span
              className="text-blue-600 font-medium cursor-default"
              onClick={goToHome}
            >
              Voltar ao Encurtador
            </span>
          </div>
        </div>
        <div className="md:flex-1 mt-8 md:mt-0 md:pl-6 text-right">
          <img
            src="/src/assets/link-information-image.svg"
            alt="Ilustração de encurtador de URL"
            className="w-full max-w-md rounded-xl"
          />
        </div>
      </div>
      <div className="animate-spin fill-red-500 size-3.5"></div>
      {isOpenModal && dashboardInfo && (
        <DashboardModal
          isOpen={isOpenModal}
          onClose={onCloseModal}
          dashboardInfo={dashboardInfo}
        />
      )}
    </div>
  );
}
